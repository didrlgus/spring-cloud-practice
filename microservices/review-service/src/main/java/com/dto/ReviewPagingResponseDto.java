package com.dto;

import com.utils.page.PageResponseData;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewPagingResponseDto {

    private List<?> responseDtoList;
    private PageResponseData pageResponseData;

    @Builder
    public ReviewPagingResponseDto(List<?> responseDtoList, PageResponseData pageResponseData) {
        this.responseDtoList = responseDtoList;
        this.pageResponseData = pageResponseData;
    }

}
