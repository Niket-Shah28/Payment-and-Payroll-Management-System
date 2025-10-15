package com.aurionpro.payrollsystem.exception;

import org.springframework.http.HttpStatus;

public class CapatchaFailedException extends BaseCustomException{
	
	public CapatchaFailedException(String message, HttpStatus status) {
        super(message, status);
    }
}
