package com.service;

import com.common.error.ErrorCode;
import com.domain.User;
import com.domain.UserRepository;
import com.dto.users.UserAddRequestDto;
import com.dto.users.UserAddResponseDto;
import com.exception.IdentifierDuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Map<String, Object> addUser(UserAddRequestDto userAddRequestDto) {
        long count = userRepository.countByIdentifier(userAddRequestDto.getIdentifier());

        if (count >= 1L) {
            throw new IdentifierDuplicateException(ErrorCode.IDENTIFIER_DUPLICATION.getMessage());
        }

        User newUser = userRepository.save(User.builder()
                .authority("ROLE_USER")
                .identifier(userAddRequestDto.getIdentifier())
                .password(bCryptPasswordEncoder.encode(userAddRequestDto.getPassword()))
                .userName(userAddRequestDto.getUserName())
                .email(userAddRequestDto.getEmail())
                .phone(userAddRequestDto.getPhone())
                .build());

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("user", new UserAddResponseDto(newUser));

        return resultMap;
    }

}
