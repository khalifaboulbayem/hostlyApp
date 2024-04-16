package com.hostly.hostlyapp.handlers.exceptions;

public class MalformedHeaderException extends BadRequestException {
    public MalformedHeaderException(String msg) {
        super(msg);
    }
}