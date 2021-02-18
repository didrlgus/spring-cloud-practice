package com.dto.users;

import com.domain.User;
import lombok.Getter;

@Getter
public class UserAddResponseDto {

    private Long id;
    private String authority;
    private String identifier;
    private String userName;
    private String email;
    private String phone;

    public UserAddResponseDto(User user) {
        this.id = user.getId();
        this.authority = user.getAuthority();
        this.identifier = user.getIdentifier();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
    }
}
