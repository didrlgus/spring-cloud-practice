package com.domain;

import com.dto.BookRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String identifier;      // 유저 아이디

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(length = 25, nullable = false)
    private String publisher;

    @Column(length = 20)
    private String publishDate;

    @Column(length = 50)
    private String category;

    @Lob
    private String intro;

    @Lob
    private String content;

    @Column
    private String referenceUrl;

    @Column(length = 50)
    private String location;

    @Column
    private String thumbnail;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean isRent;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean isDeleted;

    @Column
    private String etc;

    @Column
    private LocalDate rentExpiredDate;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer extensionCount;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Builder
    public Book(String title, String author, String publisher, String publishDate, String category, String intro,
                String content, String referenceUrl, String location, String thumbnail, boolean isRent,
                boolean isDeleted, String etc, Integer extensionCount, LocalDateTime createdDate,
                LocalDateTime modifiedDate) {
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
        this.isDeleted = isDeleted;
        this.etc = etc;
        this.extensionCount = extensionCount;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public void update(BookRequestDto.Put bookRequestDto) {
        this.title = bookRequestDto.getTitle();
        this.author = bookRequestDto.getAuthor();
        this.publisher = bookRequestDto.getPublisher();
        this.publishDate = bookRequestDto.getPublishDate();
        this.category = bookRequestDto.getCategory();
        this.intro = bookRequestDto.getIntro();
        this.content = bookRequestDto.getContent();
        this.referenceUrl = bookRequestDto.getReferenceUrl();
        this.location = bookRequestDto.getLocation();
        this.thumbnail = bookRequestDto.getThumbnail();
        this.isRent = bookRequestDto.isRent();
        this.etc = bookRequestDto.getEtc();
    }

    public void delete() {
        this.isDeleted = true;
    }

    public void rent(String identifier) {
        this.identifier = identifier;
        this.isRent = true;
        this.rentExpiredDate = LocalDate.now().plusMonths(1);
    }

    public void extendRent() {
        this.extensionCount++;
        this.rentExpiredDate = this.rentExpiredDate.plusMonths(1);
    }

    public void returnBook() {
        this.identifier = null;
        this.isRent = false;
        this.extensionCount = 0;
        this.rentExpiredDate = null;
    }
}
