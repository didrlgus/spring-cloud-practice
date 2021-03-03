package com.controller;

import com.dto.*;
import com.service.BookService;
import com.utils.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.nio.file.AccessDeniedException;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;
    private final JwtUtils jwtUtils;

    @PostMapping("/books")
    public ResponseEntity<BookResponseDto> addBook(@RequestBody @Valid BookRequestDto.Post bookRequestDto) {

        return ResponseEntity.ok(bookService.addBook(bookRequestDto));
    }

    @GetMapping("/books")
    public ResponseEntity<BookPagingResponseDto> getBooks(@RequestParam(value = "page", required = false) Integer page) {

        return ResponseEntity.ok(bookService.getBooks(page));
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<BookResponseDto> getBookDetails(@PathVariable("id") Long id) {

        return ResponseEntity.ok(bookService.getBookDetails(id));
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<BookResponseDto> updateBook(@PathVariable("id") Long id, @RequestBody @Valid BookRequestDto.Put bookRequestDto) {

        return ResponseEntity.ok(bookService.updateBook(id, bookRequestDto));
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<BookResponseDto> deleteBook(@PathVariable("id") Long id) {

        return ResponseEntity.ok(bookService.deleteBook(id));
    }

    @PutMapping("/books/{id}/rent")
    public ResponseEntity<BookResponseDto> rentBook(@PathVariable("id") Long id, HttpServletRequest request) throws AccessDeniedException {
        String jwt = jwtUtils.getJwtFromRequest(request);

        return ResponseEntity.ok(bookService.rentBook(id, jwtUtils.getIdentifierFromJwt(jwt)));
    }

    @PutMapping("/books/{id}/extension")
    public ResponseEntity<BookResponseDto> extendRent(@PathVariable("id") Long id, HttpServletRequest request) throws AccessDeniedException {
        String jwt = jwtUtils.getJwtFromRequest(request);

        return ResponseEntity.ok(bookService.extendRent(id, jwtUtils.getIdentifierFromJwt(jwt)));
    }

    @PutMapping("/books/{id}/return")
    public ResponseEntity<BookResponseDto> returnBook(@PathVariable("id") Long id, HttpServletRequest request) throws AccessDeniedException {
        String jwt = jwtUtils.getJwtFromRequest(request);

        return ResponseEntity.ok(bookService.returnBook(id, jwtUtils.getIdentifierFromJwt(jwt)));
    }

    @PutMapping("/books/{id}/reviews")
    public ResponseEntity<ReviewResponseDto> addReviewRating(@PathVariable("id") Long bookId, @RequestBody ReviewRequestDto reviewRequestDto,
                                                             HttpServletRequest request) throws AccessDeniedException {
        String jwt = jwtUtils.getJwtFromRequest(request);

        return ResponseEntity.ok(bookService.addReviewRating(bookId, reviewRequestDto, jwtUtils.getIdentifierFromJwt(jwt)));
    }

}
