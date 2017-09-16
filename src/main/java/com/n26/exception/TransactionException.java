package com.n26.exception;

/**
 * @author tarun
 * Exception Class to handle all the exceptions throughout the application
 *
 */
public class TransactionException extends Exception {

	private static final long serialVersionUID = -6568286497092479591L;
	
	private Throwable excep;
	private String errorMessage;
	
	public Throwable getExcep() {
		return excep;
	}

	public void setExcep(Throwable excep) {
		this.excep = excep;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public TransactionException(Throwable excep, String errorMessage) {
		super();
		this.excep = excep;
		this.errorMessage = errorMessage;
	}
	
	public TransactionException(){
		super();
	}
}
