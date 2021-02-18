package com.common.error.exception;

import com.common.error.ErrorCode;

public class InvalidValueException extends BusinessException {

    public InvalidValueException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
