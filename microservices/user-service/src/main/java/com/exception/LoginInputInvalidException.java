package com.exception;

import com.common.error.ErrorCode;
import com.common.error.exception.InvalidValueException;

public class LoginInputInvalidException extends InvalidValueException {

    public LoginInputInvalidException(String message) {
        super(message);
    }

}
