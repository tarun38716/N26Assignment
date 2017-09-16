package com.n26.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author tarun
 * DTO to hold statistics data
 *
 */
public class Stats {
	
	@JsonProperty("sum")
	private double sum;
	
	@JsonProperty("avg")
	private double avg;
	
	@JsonProperty("max")
	private double max;
	
	@JsonProperty("min")
	private double min;
	
	@JsonProperty("count")
	private long count;

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}
	
	@Override
	public String toString() {
		return "Stats [sum=" + sum + ", avg=" + avg + ", max=" + max + ", min=" + min + ", count=" + count + "]";
	}

}
