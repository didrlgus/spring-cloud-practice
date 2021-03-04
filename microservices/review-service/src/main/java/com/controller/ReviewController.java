package com.controller;

import com.dto.ReviewPagingResponseDto;
import com.dto.ReviewRequestDto;
import com.dto.ReviewResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.utils.jwt.JwtUtils;
import com.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final JwtUtils jwtUtils;
    private final ReviewService reviewService;

    @PostMapping("/books/{id}/reviews")
    public ResponseEntity<ReviewResponseDto.Add> addReview(@PathVariable("id") Long bookId, @RequestBody ReviewRequestDto.Post reviewRequestDto, HttpServletRequest request)
            throws AccessDeniedException, JsonProcessingException {

        String jwt = jwtUtils.getJwtFromRequest(request);

        return ResponseEntity.ok(reviewService.addReview(bookId, reviewRequestDto, jwt));
    }

    @GetMapping("/books/reviews")
    public ResponseEntity<ReviewPagingResponseDto> getReviewsOfUser(@RequestParam(value = "page", required = false) Integer page, HttpServletRequest request) throws AccessDeniedException {
        String jwt = jwtUtils.getJwtFromRequest(request);

        return ResponseEntity.ok(reviewService.getReviewsOfUser(jwtUtils.getIdentifierFromJwt(jwt), page));
    }

    @GetMapping("/books/reviews/{id}")
    public ResponseEntity<ReviewResponseDto.Details> getReviewDetails(@PathVariable("id") Long id) {

        return ResponseEntity.ok(reviewService.getReviewDetails(id));
    }

    @GetMapping("/books/{id}/reviews")
    public ResponseEntity<ReviewPagingResponseDto> getReviewsOfBook(@PathVariable("id") Long bookId, @RequestParam(value = "page", required = false) Integer page) {

        return ResponseEntity.ok(reviewService.getReviewsOfBook(bookId, page));
    }

}
