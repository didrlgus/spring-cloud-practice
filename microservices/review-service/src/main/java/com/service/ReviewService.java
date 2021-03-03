package com.service;

import com.domain.Review;
import com.domain.ReviewRepository;
import com.dto.BookResponseDto;
import com.dto.ReviewRequestDto;
import com.dto.ReviewResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final static String ADD_REVIEW_IN_BOOK_URL = "http://book-service/books/";

    private final ReviewRepository reviewRepository;
    private final RestTemplate restTemplate;

    @Transactional
    public ReviewResponseDto addReview(Long bookId, ReviewRequestDto.Post reviewRequestDto, String identifier) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Map<String, Object> map = new HashMap<>();
        map.put("rating", reviewRequestDto.getRating());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<String> response
                = restTemplate.exchange(ADD_REVIEW_IN_BOOK_URL + bookId + "/reviews", HttpMethod.PUT, entity, String.class);

        Review review = reviewRepository.save(reviewRequestDto.toEntity(bookId, identifier));

        BookResponseDto bookResponseDto = new ObjectMapper().readValue(response.getBody(), BookResponseDto.class);

        return bookResponseDto.toReviewResponseDto(review.getIdentifier());
    }
}
