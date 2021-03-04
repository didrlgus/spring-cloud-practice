package com.domain;

public enum RentStatus {

    RENT("대여중"),
    RETURN("반납완료"),
    OVERDUE("연체중");

    RentStatus(String value) {
        this.value = value;
    }

    private final String value;
    public String value() { return value; }

}
