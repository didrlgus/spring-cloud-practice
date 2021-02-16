package com.controller;

import com.domain.User;
import com.domain.UserRepository;
import com.dto.users.UserAddRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserRepository userRepository;

    @PostMapping("/users")
    public ResponseEntity<?> addUser(@RequestBody UserAddRequestDto userAddRequestDto) {

        return null;
    }

    @GetMapping("/")
    public String test() {
        return "user success!";
    }

    @GetMapping("/admin")
    public String test2() {

        return "admin success!";
    }

    @PostMapping("/users")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        User newUser = new User();
        newUser.setIdentifier(user.getIdentifier());
        newUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        newUser.setAuthority("ROLE_USER");

        userRepository.save(newUser);

        return ResponseEntity.ok("유저 add 성공!");
    }

    @PostMapping("/admin")
    public ResponseEntity<?> addAdmin(@RequestBody User user) {
        User newUser = new User();
        newUser.setIdentifier(user.getIdentifier());
        newUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        newUser.setAuthority("ROLE_ADMIN");

        userRepository.save(newUser);

        return ResponseEntity.ok("어드민 add 성공!");
    }

    @GetMapping("/users")
    public ResponseEntity<User> getUser(@RequestParam("identifier") String identifier) {

        User authUser = userRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));

        return ResponseEntity.ok(authUser);
    }

}
