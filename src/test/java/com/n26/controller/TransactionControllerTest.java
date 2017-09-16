package com.n26.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n26.dto.Stats;
import com.n26.dto.Transaction;
import com.n26.exception.TransactionException;
import com.n26.service.TransactionService;

/**
 * @author tarun
 *
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TransactionControllerTest {
	
	@Mock
	private TransactionService transactionService;

	@InjectMocks
	private TransactionController transactionController;

	private MockMvc mockMvc;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
	}

	@Test
	public void postTransactionSuccessTest() throws Exception {
		long timeStamp = Instant.now().toEpochMilli();
		Transaction transaction = new Transaction(111.9,timeStamp);
		String json = mapper.writeValueAsString(transaction);
		when(transactionService.postTransaction((Transaction)anyObject())).thenReturn(true);
		
		mockMvc.perform(post("/n26app/transactions").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isCreated())
		.andDo(print());
	}
	
	@Test
	public void postTransactionNoContentTest() throws Exception {
		long timeStamp = Instant.now().toEpochMilli();
		Transaction transaction = new Transaction(111.9,timeStamp);
		String json = mapper.writeValueAsString(transaction);
		when(transactionService.postTransaction((Transaction)anyObject())).thenReturn(false);
		
		mockMvc.perform(post("/n26app/transactions").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isNoContent())
		.andDo(print());
	}
	
	@Test
	public void postTransactionBadRequestTest() throws Exception {
		long timeStamp = Instant.now().toEpochMilli();
		Transaction transaction = new Transaction(0,timeStamp);
		String json = mapper.writeValueAsString(transaction);
		when(transactionService.postTransaction((Transaction)anyObject())).thenReturn(false);
		
		mockMvc.perform(post("/n26app/transactions").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isBadRequest())
		.andDo(print());
	}
	
	@Test
	public void postTransactionInternalServerErrorTest() throws Exception {
		Transaction transaction = new Transaction(11.33,Instant.now().toEpochMilli());
		String json = mapper.writeValueAsString(transaction);
		when(transactionService.postTransaction((Transaction)anyObject())).thenThrow(new TransactionException(new RuntimeException(), "Error"));
		
		mockMvc.perform(post("/n26app/transactions").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isInternalServerError())
		.andExpect(content().string("Error"))
		.andDo(print());
	}
	
	@Test
	public void getTransactionsSuccessTest() throws Exception {
		long timeStamp = Instant.now().toEpochMilli();
		Transaction transaction = new Transaction(11.33,timeStamp);
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);
		when(transactionService.getTransactions()).thenReturn(transactions);
		
		mockMvc.perform(get("/n26app/transactions").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].amount", is(11.33)))
		.andExpect(jsonPath("$[0].timestamp", is(timeStamp)))
		.andDo(print());
	}
	
	@Test
	public void getTransactionsInternalServerErrorTest() throws Exception {
		when(transactionService.getTransactions()).thenThrow(new TransactionException(new RuntimeException(), "Error"));
		mockMvc.perform(get("/n26app/transactions").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isInternalServerError())
		.andExpect(content().string("Error"))
		.andDo(print());
	}
	
	@Test
	public void getStatisticsSuccessTest() throws Exception {
		Stats stat = new Stats();
		stat.setAvg(100.2);
		stat.setMax(100.2);
		stat.setMin(100.2);
		stat.setCount(2);
		stat.setSum(200.4);
		when(transactionService.getStatistics()).thenReturn(stat);
		
		mockMvc.perform(get("/n26app/statistics").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.sum", is(200.4)))
		.andExpect(jsonPath("$.avg", is(100.2)))
		.andExpect(jsonPath("$.max", is(100.2)))
		.andExpect(jsonPath("$.min", is(100.2)))
		.andExpect(jsonPath("$.count", is(2)))
		.andDo(print());
	}
	
	@Test
	public void getStatisticsInternalServerErrorTest() throws Exception {
		when(transactionService.getStatistics()).thenThrow(new TransactionException(new RuntimeException(), "Error"));
		mockMvc.perform(get("/n26app/statistics").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isInternalServerError())
		.andExpect(content().string("Error"))
		.andDo(print());
	}
	
	

}
