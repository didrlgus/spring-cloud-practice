package com.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/user")
    public String userTest() {
        System.out.println("유저 권한 입니다!");
        return "유저 성공했습니다!";
    }

    @GetMapping("/admin")
    public String adminTest() {
        System.out.println("어드민 권한 입니다!");
        return "어드민 성공했습니다!";
    }

    @PostMapping("/users")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        User newUser = new User();
        newUser.setIdentifier(user.getIdentifier());
        newUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        newUser.setRoles("ROLE_ADMIN");

        userRepository.save(newUser);

        return ResponseEntity.ok("유저 add 성공!");
    }

    @GetMapping("/users")
    public ResponseEntity<User> getUser(@RequestParam("username") String identifier) {

        User authUser = userRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));

        return ResponseEntity.ok(authUser);
    }
}
