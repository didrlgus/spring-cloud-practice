package com.dto;

import com.utils.page.PageResponseData;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewPagingResponseDto {

    private List<ReviewResponseDto.List> reviewResponseDtoLIst;
    private PageResponseData pageResponseData;

    @Builder
    public ReviewPagingResponseDto(List<ReviewResponseDto.List> reviewResponseDtoLIst, PageResponseData pageResponseData) {
        this.reviewResponseDtoLIst = reviewResponseDtoLIst;
        this.pageResponseData = pageResponseData;
    }

}
