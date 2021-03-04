package com.exception;

public class InvalidRentStatusException extends BusinessException {

    public InvalidRentStatusException(String errorMessage) {
        super(errorMessage);
    }
}
