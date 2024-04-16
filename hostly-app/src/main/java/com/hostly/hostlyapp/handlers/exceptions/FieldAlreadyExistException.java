package com.hostly.hostlyapp.handlers.exceptions;

public class FieldAlreadyExistException extends ConflictException {
    public FieldAlreadyExistException(String msg) {
        super(msg);
    }
}
