package com.service;

import com.domain.Review;
import com.domain.ReviewRepository;
import com.dto.BookRequestDto;
import com.dto.ReviewRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("restTemplate")
    private final RestTemplate restTemplate;
    @Qualifier("restTemplateForPutAndPatch")
    private final RestTemplate restTemplateForPutAndPatch;

    @Transactional
    public void addReview(ReviewRequestDto.Post reviewRequestDto, String identifier) {
        Review review = reviewRepository.save(reviewRequestDto.toEntity(identifier));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Map<String, Object> map = new HashMap<>();
        map.put("rating", reviewRequestDto.getRating());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<String> response
                = restTemplateForPutAndPatch.exchange(ADD_REVIEW_IN_BOOK_URL + reviewRequestDto.getBookId() + "/reviews", HttpMethod.PATCH, entity, String.class);

        System.out.println(response);
    }
}
