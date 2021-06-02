package com.ttu.rbt.exceptions;

public class EmailNotSentException extends Exception { 

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2908837649315927811L;

	public EmailNotSentException(String message) {
		super(message);
	}
	
}
