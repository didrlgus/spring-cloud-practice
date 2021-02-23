package com.common.error;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE("C001", "Invalid Input Value"),
    METHOD_NOT_ALLOWED("C002", "method not allowed"),
    ENTITY_NOT_FOUND("C003", "Entity Not Found"),
    INTERNAL_SERVER_ERROR("C004", "Server Error"),
    INVALID_TYPE_VALUE("C005", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED("C006", "Access is Denied"),

    // User
    IDENTIFIER_DUPLICATION("U001", "현재 사용중인 아이디 입니다."),
    LOGIN_INPUT_INVALID("U002", "정확하지 않은 아이디 또는 비밀번호 입니다."),
    NOT_EXIST_USER("U003", "존재하지 않는 사용자 입니다.");

    private final String code;
    private final String message;

    ErrorCode(final String code, final String message) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

}
