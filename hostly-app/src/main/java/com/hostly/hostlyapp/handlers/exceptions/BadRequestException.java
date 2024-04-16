package com.hostly.hostlyapp.handlers.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }

}
