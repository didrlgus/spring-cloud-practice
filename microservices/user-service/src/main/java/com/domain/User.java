package com.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String authority;

    @Column
    private String identifier;

    @Column
    private String password;

    @Column
    private String userName;

    @Column
    private String email;

    @Column
    private String phone;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Builder
    public User(String authority, String identifier, String password, String userName, String email, String phone) {
        this.authority = authority;
        this.identifier = identifier;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
    }

}
