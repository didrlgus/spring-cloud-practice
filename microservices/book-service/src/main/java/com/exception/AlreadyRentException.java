package com.exception;

public class AlreadyRentException extends BusinessException {

    public AlreadyRentException(String errorMessage) {
        super(errorMessage);
    }
}
