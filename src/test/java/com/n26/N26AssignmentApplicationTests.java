package com.n26;

import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.n26.dto.Stats;
import com.n26.dto.Transaction;

/**
 * @author tarun
 * Integration Tests
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class N26AssignmentApplicationTests {
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	@Test
	public void test() {
		Transaction transaction = new Transaction(10.00,Instant.now().toEpochMilli());
		ResponseEntity<String> entity =  restTemplate.postForEntity("/n26app/transactions", transaction, String.class);
		assertTrue(entity.getStatusCode() == HttpStatus.CREATED);
		entity =  restTemplate.postForEntity("/n26app/transactions", new Transaction(11.33,Instant.now().toEpochMilli()-60001), String.class);
		assertTrue(entity.getStatusCode() == HttpStatus.NO_CONTENT);
		entity =  restTemplate.postForEntity("/n26app/transactions", new Transaction(20.00,Instant.now().toEpochMilli()), String.class);
		assertTrue(entity.getStatusCode() == HttpStatus.CREATED);
		
		ResponseEntity<List> transactions = restTemplate.getForEntity("/n26app/transactions", List.class);
		assertTrue(transactions.getBody().size() == 2);
		assertTrue(transactions.getStatusCode() == HttpStatus.OK);
		
		ResponseEntity<Stats> statistics = restTemplate.getForEntity("/n26app/statistics", Stats.class);
		assertTrue(statistics.getBody().getCount()== 2);
		assertTrue(statistics.getBody().getSum()== 30.00);
		assertTrue(statistics.getBody().getAvg()== 15.00);
		assertTrue(statistics.getBody().getCount()== 2);
		assertTrue(statistics.getBody().getMax()== 20.00);
		assertTrue(statistics.getBody().getMin()== 10.00);
		assertTrue(statistics.getStatusCode() == HttpStatus.OK);
	}
}
