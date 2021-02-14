package com.config;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
    private Long id;
    private String identifier;
    private String password;
    private String roles;
}
