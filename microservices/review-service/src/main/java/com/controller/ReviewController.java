package com.controller;

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

    @PutMapping("/books/{id}/reviews")
    public ResponseEntity<ReviewResponseDto> addReview(@PathVariable("id") Long bookId, @RequestBody ReviewRequestDto.Post reviewRequestDto, HttpServletRequest request)
            throws AccessDeniedException, JsonProcessingException {

        String jwt = jwtUtils.getJwtFromRequest(request);

        return ResponseEntity.ok(reviewService.addReview(bookId, reviewRequestDto, jwt));
    }

}
