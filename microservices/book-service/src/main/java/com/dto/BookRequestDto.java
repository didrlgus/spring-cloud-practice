package com.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class BookRequestDto {

    @Setter
    @Getter
    @NoArgsConstructor
    public static class Post {
        private String title;
        private String author;
        private String publisher;
        private String publishDate;
        private String category;
        private String intro;
        private String content;
        private String url;
        private String location;
        private String thumbnail;
        private String etc;

        @Builder
        public Post(String title, String author, String publisher, String publishDate, String category,
                    String intro, String content, String url, String location, String thumbnail, String etc) {
            this.title = title;
            this.author = author;
            this.publisher = publisher;
            this.publishDate = publishDate;
            this.category = category;
            this.intro = intro;
            this.content = content;
            this.url = url;
            this.location = location;
            this.thumbnail = thumbnail;
            this.etc = etc;
        }
    }

}
