package com.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class BookResponseDto {

    private Long id;
    private String identifier;
    private String title;
    private String author;
    private String publisher;
    private String publishDate;
    private String category;
    private String intro;
    private String content;
    private String referenceUrl;
    private String location;
    private String thumbnail;
    private boolean isRent;
    private String etc;
    private Integer extensionCount;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate rentExpiredDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedDate;

}
