package com.n26.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.n26.dao.TransactionDao;
import com.n26.dto.Stats;
import com.n26.dto.Transaction;
import com.n26.exception.TransactionException;

/**
 * @author tarun
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

	@Mock
	private TransactionDao transactionDao;

	@InjectMocks
	private TransactionService transactionService;

	@Test
	public void postTransactionSuccessTest() throws TransactionException {
		Transaction transaction = new Transaction(11.33,Instant.now().toEpochMilli());
		assertTrue(transactionService.postTransaction(transaction));
	}

	@Test
	public void postTransactionNoContentTest() throws TransactionException {
		Transaction transaction = new Transaction(11.33,Instant.now().toEpochMilli()+60000);
		assertFalse(transactionService.postTransaction(transaction));
	}

	@Test
	public void getTransactionsTest() throws TransactionException {
		Transaction transaction = new Transaction(11.33,Instant.now().toEpochMilli());
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);
		when(transactionDao.getTransactions()).thenReturn(transactions);
		assertFalse(transactionService.getTransactions().isEmpty());
	}

	@Test
	public void getTransactionsExceptionTest() {
		when(transactionDao.getTransactions()).thenThrow(new RuntimeException());
		try {
			transactionService.getTransactions();
		} catch (TransactionException e) {
			assertThat(e.getErrorMessage().equals("Internal Error : Please try again later"));
		}
	}

	@Test
	public void getStatisticsExceptionTest() {
		when(transactionDao.getTransactions()).thenThrow(new RuntimeException());
		try {
			transactionService.getStatistics();
		} catch (TransactionException e) {
			assertThat(e.getErrorMessage().equals("Internal Error : Please try again later"));
		}
	}
	
	@Test
	public void getStatisticsTest() throws TransactionException {
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(new Transaction(11.00,Instant.now().toEpochMilli()));
		transactions.add(new Transaction(10.00,Instant.now().toEpochMilli()));
		when(transactionDao.getTransactions()).thenReturn(transactions);
		Stats stat = transactionService.getStatistics();
		assertNotNull(stat);
		assertThat(stat.getCount()==2);
		assertThat(stat.getSum()==20.00);
	}

}
