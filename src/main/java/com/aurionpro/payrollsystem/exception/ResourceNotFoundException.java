package com.aurionpro.payrollsystem.exception;

import org.springframework.http.HttpStatus;


public class ResourceNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private String message;
	 HttpStatus status;

	public ResourceNotFoundException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
