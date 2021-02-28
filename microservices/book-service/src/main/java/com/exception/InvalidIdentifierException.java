package com.exception;

import com.common.exception.BusinessException;
import com.utils.error.ErrorMessage;

public class InvalidIdentifierException extends BusinessException {

    public InvalidIdentifierException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
