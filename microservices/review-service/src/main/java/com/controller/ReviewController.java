package com.controller;

import com.dto.ReviewRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.utils.jwt.JwtUtils;
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

    private final JwtUtils jwtUtils;
    private final ReviewService reviewService;

    @PostMapping("/reviews")
    public ResponseEntity<?> addReview(@RequestBody ReviewRequestDto.Post reviewRequestDto, HttpServletRequest request)
            throws AccessDeniedException, JsonProcessingException {

        String jwt = jwtUtils.getJwtFromRequest(request);

        reviewService.addReview(reviewRequestDto, jwtUtils.getIdentifierFromJwt(jwt));

        return null;
    }

}
