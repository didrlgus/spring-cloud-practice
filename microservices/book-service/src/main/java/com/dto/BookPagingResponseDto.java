package com.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BookPagingResponseDto {

    private List<BookResponseDto> bookResponseDtoList;
    private PageUtils pageUtils;

    @Builder
    public BookPagingResponseDto(List<BookResponseDto> bookResponseDtoList, PageUtils pageUtils) {
        this.bookResponseDtoList = bookResponseDtoList;
        this.pageUtils = pageUtils;
    }

}
