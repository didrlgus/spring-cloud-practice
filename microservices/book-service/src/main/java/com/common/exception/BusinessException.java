package com.common.exception;


import com.utils.error.ErrorMessage;

public class BusinessException extends RuntimeException {

    private final ErrorMessage errorMessage;


    public BusinessException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

}
