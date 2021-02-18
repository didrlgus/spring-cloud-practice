package com.exception;

import com.common.error.ErrorCode;
import com.common.error.exception.InvalidValueException;

public class IdentifierDuplicateException extends InvalidValueException {

    public IdentifierDuplicateException(String message) {
        super(message, ErrorCode.IDENTIFIER_DUPLICATION);
    }

}
