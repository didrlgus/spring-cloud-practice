package com.exception;

public class InvalidIdentifierException extends BusinessException {

    public InvalidIdentifierException(String errorMessage) {
        super(errorMessage);
    }
}
