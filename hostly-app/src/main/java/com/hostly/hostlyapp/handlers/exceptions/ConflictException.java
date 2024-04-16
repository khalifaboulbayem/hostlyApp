package com.hostly.hostlyapp.handlers.exceptions;

public class ConflictException extends RuntimeException {
    public ConflictException(String msg) {
        super(msg);
    }
}
