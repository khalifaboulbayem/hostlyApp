package com.hostly.hostlyapp.handlers.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String msg) {
        super(msg);
    }

}
