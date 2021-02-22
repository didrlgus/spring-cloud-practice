package com.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String author;

    @Column(length = 25, nullable = false)
    private String publisher;

    @Column
    private LocalDateTime publishDate;

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

    @Column(nullable = false)
    private String etc;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus requestStatus;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Builder
    public Book(String author, String publisher, LocalDateTime publishDate, String category, String intro,
                String content, String referenceUrl, String location, String thumbnail, boolean isRent,
                boolean isDeleted, String etc, RequestStatus requestStatus, LocalDateTime createdDate,
                LocalDateTime modifiedDate) {
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
        this.requestStatus = requestStatus;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
