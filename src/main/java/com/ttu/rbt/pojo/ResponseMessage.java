package com.ttu.rbt.pojo;

import org.springframework.stereotype.Component;

@Component
public class ResponseMessage {

	private String message;

	private int status;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
