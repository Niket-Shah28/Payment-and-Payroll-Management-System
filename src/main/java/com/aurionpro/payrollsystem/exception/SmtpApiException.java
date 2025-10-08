package com.aurionpro.payrollsystem.exception;

import org.springframework.http.HttpStatus;


public class SmtpApiException extends BaseCustomException{
	
	public SmtpApiException(String message) {
		super(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
