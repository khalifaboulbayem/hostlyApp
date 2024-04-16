package com.hostly.hostlyapp.handlers.exceptions;

public class FieldInvalidException extends BadRequestException {
    public FieldInvalidException(String msg) {
        super(msg);
    }

}
