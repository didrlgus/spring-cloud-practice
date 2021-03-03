package com.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long bookId;

    @Column(length = 25, nullable = false)
    private String identifier;                              // 유저 아이디

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer rating;

    @Lob
    private String content;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean isDeleted;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Builder
    public Review(Long bookId, String identifier, String title, Integer rating, String content, boolean isDeleted) {
        this.bookId = bookId;
        this.identifier = identifier;
        this.title = title;
        this.rating = rating;
        this.content = content;
        this.isDeleted = isDeleted;
    }
}
