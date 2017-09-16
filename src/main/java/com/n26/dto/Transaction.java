package com.n26.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author tarun
 * DTO to hold transaction Data
 *
 */
public class Transaction {
	
	@JsonProperty("amount")
	private double amount;
	
	@JsonProperty("timestamp")
	private long timestamp;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Transaction [amount=" + amount + ", timestamp=" + timestamp + "]";
	}

	public Transaction(double amount, long timestamp) {
		super();
		this.amount = amount;
		this.timestamp = timestamp;
	}

	public Transaction() {
		super();
	}
}
