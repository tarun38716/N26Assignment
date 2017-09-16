package com.n26.dao;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Repository;

import com.n26.dto.Transaction;

/**
 * @author tarun
 * Inmemory Hold the transactions 
 *
 */
@Repository("TransactionDao")
public class TransactionDao {
	
	private List<Transaction> transactions = new CopyOnWriteArrayList<Transaction>();

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public synchronized void addTransaction(Transaction transaction){
		transactions.add(transaction);
	}
}
