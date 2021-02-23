package com.controller;

import com.common.error.ErrorCode;
import com.common.error.exception.EntityNotFoundException;
import com.domain.User;
import com.domain.UserRepository;
import com.dto.users.UserAddRequestDto;
import com.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<Map<String, Object>> addUser(@RequestBody @Valid UserAddRequestDto userAddRequestDto) {

        return ResponseEntity.ok(userService.addUser(userAddRequestDto));
    }

    @GetMapping("/users")
    public ResponseEntity<User> getUser(@RequestParam("identifier") String identifier) {

        User authUser = userRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND.getMessage()));

        return ResponseEntity.ok(authUser);
    }

    @GetMapping("/test")
    public ResponseEntity<?> adminTest(HttpServletRequest request) {
        String token = request.getHeader("Token");

        return ResponseEntity.ok(token);
    }
}
