package com.dto;

import com.domain.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ReviewRequestDto {

    @Getter
    @NoArgsConstructor
    public static class Post {
        @Size(min = 1, max = 255)
        @NotNull(message = "제목은 null일 수 없습니다.")
        private String title;

        @Min(0)
        @Max(5)
        @NotNull(message = "평점은 null일 수 없습니다.")
        private Integer rating;

        @NotNull(message = "내용은 null일 수 없습니다.")
        private String content;

        @Builder
        public Post(String title, Integer rating, String content) {
            this.title = title;
            this.rating = rating;
            this.content = content;
        }

        public Review toEntity(Long bookId, String identifier) {
            return Review.builder()
                    .bookId(bookId)
                    .title(this.title)
                    .identifier(identifier)
                    .rating(this.rating)
                    .content(this.content)
                    .isDeleted(false)
                    .build();
        }
    }

}
