package com.utils.error;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorMessage {

    // Common
    INVALID_INPUT_VALUE("Invalid Input Value"),
    METHOD_NOT_ALLOWED("method not allowed"),
    ENTITY_NOT_FOUND("Entity Not Found"),
    INTERNAL_SERVER_ERROR("Server Error"),
    HANDLE_ACCESS_DENIED("Access is Denied"),

    // Book Business
    INVALID_PAGE_VALUE("유효하지 않은 페이지 값 입니다.");

    private final String message;

    ErrorMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}
