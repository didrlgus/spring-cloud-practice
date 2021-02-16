package com.dto.users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserAddRequestDto {

    private String identifier;

    private String password;

    private String name;

    private String email;

    private String phone;

    @Builder
    public UserAddRequestDto(String identifier, String password, String name, String email, String phone) {
        this.identifier = identifier;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

}
