package com.controller;

import com.dto.BookRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookServiceController {

    @PostMapping("/books")
    public ResponseEntity<?> addBook(@RequestBody BookRequestDto.Post bookRequestDto) {

        return ResponseEntity.ok(bookRequestDto);
    }

}
