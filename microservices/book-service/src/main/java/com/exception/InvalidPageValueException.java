package com.exception;

import com.common.exception.BusinessException;
import com.utils.error.ErrorMessage;

public class InvalidPageValueException extends BusinessException {

    public InvalidPageValueException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

}
