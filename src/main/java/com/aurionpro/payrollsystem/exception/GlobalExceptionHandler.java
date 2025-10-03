package com.aurionpro.payrollsystem.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.jsonwebtoken.JwtException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler
	public ResponseEntity<Map<String, String>> handleMethodArguementNotValidException(MethodArgumentNotValidException exception){
		Map<String, String> errors=new HashMap<>();
	    
	    exception.getBindingResult().getFieldErrors().forEach(
	        (error)->{
	          errors.put(error.getField(), error.getDefaultMessage());
	        });
	    logger.error(errors.toString());
	    return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({ BadCredentialsException.class, JwtException.class })
	public ResponseEntity<Map<String, String>> handleAuthExceptions(RuntimeException exception) {
	    logger.error(exception.getMessage());
	    return ResponseEntity
	            .status(HttpStatus.UNAUTHORIZED)
	            .body(Map.of("error", exception.getMessage()));
	}
	
	@ExceptionHandler
	public ResponseEntity<Map<String, String>> handleOrganizationApplicationRequestException(OrganizationApplicationRequestException exception) {
	    logger.error(exception.getMessage());
	    return ResponseEntity
	            .status(exception.getStatus())
	            .body(Map.of("error", exception.getMessage()));
	}
}
