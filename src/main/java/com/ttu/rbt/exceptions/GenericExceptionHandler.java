package com.ttu.rbt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GenericExceptionHandler {

	@ExceptionHandler(UserNotFound.class)
	public ResponseEntity<ErrorResponse> handleException(UserNotFound ex) {
		ErrorResponse exceptionResponse = new ErrorResponse();
		exceptionResponse.setMessage(ex.getMessage());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(EmailNotSentException.class)
	public ResponseEntity<ErrorResponse> handleException(EmailNotSentException ex) {
		ErrorResponse exceptionResponse = new ErrorResponse();
		exceptionResponse.setMessage(ex.getMessage());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}
}
