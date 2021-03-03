package com.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDto {

    private String identifier;

    private Integer avgReviewRating;

    private Integer reviewCount;

}
