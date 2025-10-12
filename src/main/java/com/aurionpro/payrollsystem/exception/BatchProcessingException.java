package com.aurionpro.payrollsystem.exception;

import org.springframework.http.HttpStatus;

public class BatchProcessingException extends BaseCustomException{
	
	public BatchProcessingException(String message, HttpStatus status) {
        super(message, status);
    }

}
