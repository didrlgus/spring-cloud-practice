package com.dto;

import com.domain.Book;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BookRequestDto {

    @Getter
    @NoArgsConstructor
    public static class Post {
        @Size(min = 1, max = 255)
        @NotNull(message = "제목은 null 일 수 없습니다.")
        private String title;

        @Size(min = 1, max = 255)
        @NotNull(message = "저자는 null 일 수 없습니다.")
        private String author;

        @Size(max = 25)
        private String publisher;

        @Size(max = 20)
        private String publishDate;

        @Size(max = 50)
        private String category;

        private String intro;

        private String content;

        @Size(max = 255)
        private String referenceUrl;

        @Size(max = 50)
        private String location;

        @Size(max = 255)
        private String thumbnail;

        @Size(max = 255)
        private String etc;

        @Builder
        public Post(String title, String author, String publisher, String publishDate, String category,
                    String intro, String content, String referenceUrl, String location, String thumbnail, String etc) {
            this.title = title;
            this.author = author;
            this.publisher = publisher;
            this.publishDate = publishDate;
            this.category = category;
            this.intro = intro;
            this.content = content;
            this.referenceUrl = referenceUrl;
            this.location = location;
            this.thumbnail = thumbnail;
            this.etc = etc;
        }

        public Book toEntity() {
            return Book.builder()
                    .title(this.title)
                    .author(this.author)
                    .publisher(this.publisher)
                    .publishDate(this.publishDate)
                    .category(this.category)
                    .intro(this.intro)
                    .content(this.content)
                    .referenceUrl(this.referenceUrl)
                    .location(this.location)
                    .thumbnail(this.thumbnail)
                    .etc(this.etc)
                    .extensionCount(0)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Put {
        @Size(min = 1, max = 255)
        @NotNull(message = "제목은 null 일 수 없습니다.")
        private String title;

        @Size(min = 1, max = 255)
        @NotNull(message = "제목은 null 일 수 없습니다.")
        private String author;

        @Size(max = 25)
        private String publisher;

        @Size(max = 20)
        private String publishDate;

        @Size(max = 50)
        private String category;

        private String intro;

        private String content;

        @Size(max = 255)
        private String referenceUrl;

        @Size(max = 50)
        private String location;

        @Size(max = 255)
        private String thumbnail;

        private boolean isRent;

        @Size(max = 255)
        private String etc;

        public Put(String title, String author, String publisher, String publishDate, String category, String intro, String content,
                   String referenceUrl, String location, String thumbnail, boolean isRent, String etc) {
            this.title = title;
            this.author = author;
            this.publisher = publisher;
            this.publishDate = publishDate;
            this.category = category;
            this.intro = intro;
            this.content = content;
            this.referenceUrl = referenceUrl;
            this.location = location;
            this.thumbnail = thumbnail;
            this.isRent = isRent;
            this.etc = etc;
        }
    }

}
