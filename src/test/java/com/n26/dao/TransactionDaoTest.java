package com.n26.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

import java.time.Instant;

import org.junit.Test;

import com.n26.dto.Transaction;

/**
 * @author tarun
 *
 */
public class TransactionDaoTest {
	
	private TransactionDao transactionDao = new TransactionDao();

	@Test
	public void DataPersistTest() {
		transactionDao.addTransaction(new Transaction(12.33,Instant.now().toEpochMilli()));
		transactionDao.addTransaction(new Transaction(101.33,Instant.now().toEpochMilli()));
		transactionDao.addTransaction(new Transaction(111.33,Instant.now().toEpochMilli()));
		assertFalse(transactionDao.getTransactions().isEmpty());
		assertThat(transactionDao.getTransactions().get(0).getAmount() == 12.33);
	}

}
