package com.common.exception;


import com.utils.error.ErrorMessage;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
