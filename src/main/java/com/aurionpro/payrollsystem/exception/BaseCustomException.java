package com.aurionpro.payrollsystem.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class BaseCustomException extends RuntimeException {

    private final HttpStatus status;

    public BaseCustomException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}

