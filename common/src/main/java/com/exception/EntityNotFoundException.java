package com.exception;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
