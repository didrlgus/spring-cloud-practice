package com.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class BookResponseDto {

    private Long id;
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
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

}
