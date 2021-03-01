package com.dto;

import lombok.*;

@ToString
@Setter
@Getter
@NoArgsConstructor
public class ReviewRequestDto {

    Integer rating;

    @Builder
    public ReviewRequestDto(Integer rating) {
        this.rating = rating;
    }
}
