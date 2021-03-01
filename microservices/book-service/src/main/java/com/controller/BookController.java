package com.controller;

import com.dto.BookPagingResponseDto;
import com.dto.BookRequestDto;
import com.dto.BookResponseDto;
import com.dto.ReviewRequestDto;
import com.service.BookService;
import com.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.nio.file.AccessDeniedException;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;
    private final JwtService jwtService;

    @PostMapping("/books")
    public ResponseEntity<BookResponseDto> addBook(@RequestBody @Valid BookRequestDto.Post bookRequestDto) {

        return ResponseEntity.status(CREATED).body(bookService.addBook(bookRequestDto));
    }

    @GetMapping("/books")
    public ResponseEntity<BookPagingResponseDto> getBooks(@RequestParam(value = "page", required = false) Integer page) {

        return ResponseEntity.status(OK).body(bookService.getBooks(page));
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<BookResponseDto> getBookDetails(@PathVariable("id") Long id) {

        return ResponseEntity.status(OK).body(bookService.getBookDetails(id));
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<BookResponseDto> updateBook(@PathVariable("id") Long id, @RequestBody @Valid BookRequestDto.Put bookRequestDto) {

        return ResponseEntity.status(NO_CONTENT).body(bookService.updateBook(id, bookRequestDto));
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<BookResponseDto> deleteBook(@PathVariable("id") Long id) {

        return ResponseEntity.status(NO_CONTENT).body(bookService.deleteBook(id));
    }

    @PutMapping("/books/{id}/rent")
    public ResponseEntity<BookResponseDto> rentBook(@PathVariable("id") Long id, HttpServletRequest request) throws AccessDeniedException {
        String jwt = jwtService.getJwtFromHeader(request);

        return ResponseEntity.ok(bookService.rentBook(id, jwtService.getIdentifierFromJwt(jwt)));
    }

    @PutMapping("/books/{id}/return")
    public ResponseEntity<BookResponseDto> returnBook(@PathVariable("id") Long id, HttpServletRequest request) throws AccessDeniedException {
        String jwt = jwtService.getJwtFromHeader(request);

        return ResponseEntity.ok(bookService.returnBook(id, jwtService.getIdentifierFromJwt(jwt)));
    }

    @PutMapping("/books/{id}/extension")
    public ResponseEntity<BookResponseDto> extendRent(@PathVariable("id") Long id, HttpServletRequest request) throws AccessDeniedException {
        String jwt = jwtService.getJwtFromHeader(request);

        return ResponseEntity.ok(bookService.extendRent(id, jwtService.getIdentifierFromJwt(jwt)));
    }

    @PatchMapping("/books/{id}/reviews")
    public ResponseEntity<?> addReview(@PathVariable("id") Long id, @RequestBody ReviewRequestDto reviewRequestDto) {

        System.out.println(id);
        System.out.println(reviewRequestDto);

        return ResponseEntity.ok("success!!");
    }

}
