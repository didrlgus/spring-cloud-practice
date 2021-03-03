package com.service;

import com.config.JwtConfig;
import com.domain.ReviewRepository;
import com.dto.BookRequestDto;
import com.dto.ReviewRequestDto;
import com.dto.ReviewResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final static String ADD_REVIEW_IN_BOOK_URL = "http://book-service/books/";

    private final ReviewRepository reviewRepository;
    private final RestTemplate restTemplate;
    private final JwtConfig jwtConfig;

    @Transactional
    public ReviewResponseDto addReview(Long bookId, ReviewRequestDto.Post reviewRequestDto, String jwt) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(jwtConfig.getHeader(), jwtConfig.getPrefix() + " " + jwt);

        HttpEntity<BookRequestDto> entity = new HttpEntity<>(BookRequestDto.builder().rating(reviewRequestDto.getRating()).build(), headers);

        ResponseEntity<String> response
                = restTemplate.exchange(ADD_REVIEW_IN_BOOK_URL + bookId + "/reviews", HttpMethod.PUT, entity, String.class);

        ReviewResponseDto reviewResponseDto = new ObjectMapper().readValue(response.getBody(), ReviewResponseDto.class);

        reviewRepository.save(reviewRequestDto.toEntity(bookId, reviewResponseDto.getIdentifier()));

        return reviewResponseDto;
    }
}
