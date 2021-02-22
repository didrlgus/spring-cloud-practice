package com.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RequestStatus {

    COMPLETED("COMPLETED", "신청완료"),
    DISPLAYING("DISPLAYING", "비치중"),
    BUYING("BUYING", "구매중");

    private final String key;
    private final String status;
}
