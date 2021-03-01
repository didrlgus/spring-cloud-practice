package com.controller;

import com.dto.ReviewRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.service.JwtService;
import com.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final JwtService jwtService;
    private final ReviewService reviewService;

    @PostMapping("/reviews")
    public ResponseEntity<?> addReview(@RequestBody ReviewRequestDto.Post reviewRequestDto, HttpServletRequest request)
            throws AccessDeniedException, JsonProcessingException {

        String jwt = jwtService.getJwtFromHeader(request);

        reviewService.addReview(reviewRequestDto, jwtService.getIdentifierFromJwt(jwt));

        return null;
    }

}
