package com.ttu.rbt.exceptions;

public class ErrorResponse {

	private String message;

	public ErrorResponse() {
		// no argument constructor
	}

	public ErrorResponse(String message, String subCode) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
