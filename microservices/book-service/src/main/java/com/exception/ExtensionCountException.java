package com.exception;

import com.common.exception.BusinessException;
import com.utils.error.ErrorMessage;

public class ExtensionCountException extends BusinessException {

    public ExtensionCountException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
