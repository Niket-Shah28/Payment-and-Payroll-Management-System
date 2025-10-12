package com.aurionpro.payrollsystem.exception;

import org.springframework.http.HttpStatus;

public class OrganizationApplicationRequestException extends BaseCustomException{
	public OrganizationApplicationRequestException(String message, HttpStatus status) {
        super(message, status);
    }
}
