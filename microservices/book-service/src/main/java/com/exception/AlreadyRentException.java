package com.exception;

import com.common.exception.BusinessException;
import com.utils.error.ErrorMessage;

public class AlreadyRentException extends BusinessException {

    public AlreadyRentException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
