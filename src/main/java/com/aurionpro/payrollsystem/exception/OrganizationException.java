package com.aurionpro.payrollsystem.exception;

import org.springframework.http.HttpStatus;

public class OrganizationException extends BaseCustomException{
	public OrganizationException(String message, HttpStatus status) {
        super(message, status);
    }
}
