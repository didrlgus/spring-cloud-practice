package com.exception;

public class InvalidReviewIdentifierException extends BusinessException {

    public InvalidReviewIdentifierException(String errorMessage) {
        super(errorMessage);
    }
}
