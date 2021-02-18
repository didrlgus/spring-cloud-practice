package com.dto.users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
public class UserAddRequestDto {

    private String identifier;

    private String password;

    private String userName;

    @NotBlank
    @Email(message = "이메일의 양식을 지켜주세요.")
    private String email;

    private String phone;

    @Builder
    public UserAddRequestDto(String identifier, String password, String userName, String email, String phone) {
        this.identifier = identifier;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
    }

}
