package com.domain.book;

public enum BookAlertType {

    REQUESTED("도서신청"),
    RENT("도서대여"),
    RETURN("도서반납");

    BookAlertType(String value) {
        this.value = value;
    }

    private final String value;
    public String value() { return value; }
}
