package com.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

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
}
