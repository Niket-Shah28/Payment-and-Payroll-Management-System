package com.aurionpro.payrollsystem.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class OrganizationApplicationRequestException extends RuntimeException{
	private String message;
	private HttpStatus status;
}
