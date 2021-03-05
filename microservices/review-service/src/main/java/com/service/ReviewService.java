package com.service;

import com.config.JwtConfig;
import com.domain.Review;
import com.domain.ReviewRepository;
import com.dto.BookRequestDto;
import com.dto.ReviewRequestDto;
import com.dto.ReviewResponseDto;
import com.exception.EntityNotFoundException;
import com.exception.InvalidPageValueException;
import com.exception.InvalidReviewIdentifierException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapper.ReviewMapper;
import com.utils.jwt.JwtUtils;
import com.utils.page.PageUtils;
import com.utils.page.PagingResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static com.exception.message.CommonExceptionMessage.ENTITY_NOT_FOUND;
import static com.exception.message.CommonExceptionMessage.INVALID_PAGE_VALUE;
import static com.exception.message.ReviewExceptionMessage.INVALID_REVIEW_IDENTIFIER;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final static String REVIEW_IN_BOOK_URL = "http://book-service/books/";

    private final ReviewRepository reviewRepository;
    private final RestTemplate restTemplate;
    private final JwtUtils jwtUtils;
    private final PageUtils pageUtils;

    private static final int REVIEWS_OF_USER_PAGE = 10;
    private static final int REVIEW_OF_USER_SCALE_SIZE = 10;
    private static final int REVIEWS_OF_BOOK_PAGE = 10;
    private static final int REVIEW_OF_BOOK_SCALE_SIZE = 10;

    @Transactional
    public ReviewResponseDto.Add addReview(Long bookId, ReviewRequestDto.Post reviewRequestDto, String jwt) throws JsonProcessingException {
        HttpEntity<BookRequestDto> entity = new HttpEntity<>(BookRequestDto.builder().rating(reviewRequestDto.getRating()).build(),
                jwtUtils.getHttpHeadersIncludedJwt(jwt));

        ResponseEntity<String> response
                = restTemplate.exchange(REVIEW_IN_BOOK_URL + bookId + "/reviews", HttpMethod.POST, entity, String.class);

        ReviewResponseDto.Add reviewResponseDto = new ObjectMapper().readValue(response.getBody(), ReviewResponseDto.Add.class);

        reviewRepository.save(reviewRequestDto.toEntity(bookId, reviewResponseDto.getIdentifier()));

        return reviewResponseDto;
    }

    public PagingResponseDto getReviewsOfUser(String identifier, Integer page) {
        if (pageUtils.isInvalidPageValue(page)) {
            throw new InvalidPageValueException(INVALID_PAGE_VALUE);
        }

        Page<Review> reviewPage = reviewRepository.findAllByIsDeletedAndIdentifier(false, identifier,
                pageUtils.getPageable(page, REVIEWS_OF_USER_PAGE, Sort.Direction.DESC, "createdDate"));

        return pageUtils.getCommonPagingResponseDto(reviewPage, getReviewResponseDtoList(reviewPage), REVIEW_OF_USER_SCALE_SIZE);
    }

    public ReviewResponseDto.Details getReviewDetails(Long id) {

        return ReviewMapper.INSTANCE.reviewToReviewDetailsResponseDto(reviewRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException(ENTITY_NOT_FOUND)));
    }

    public PagingResponseDto getReviewsOfBook(Long bookId, Integer page) {
        if (pageUtils.isInvalidPageValue(page)) {
            throw new InvalidPageValueException(INVALID_PAGE_VALUE);
        }

        Page<Review> reviewPage = reviewRepository.findAllByIsDeletedAndBookId(false, bookId,
                pageUtils.getPageable(page, REVIEWS_OF_BOOK_PAGE, Sort.Direction.DESC, "createdDate"));

        return pageUtils.getCommonPagingResponseDto(reviewPage, getReviewResponseDtoList(reviewPage), REVIEW_OF_BOOK_SCALE_SIZE);
    }

    @Transactional
    public ReviewResponseDto.List updateReview(Long id, ReviewRequestDto.Put reviewRequestDto, String jwt) {
        Review review = reviewRepository.findByIdAndIsDeleted(id, false).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        String identifier = jwtUtils.getIdentifierFromJwt(jwt);

        if(!review.getIdentifier().equals(identifier)) {
            throw new InvalidReviewIdentifierException(INVALID_REVIEW_IDENTIFIER);
        }

        review.update(reviewRequestDto);

        return ReviewMapper.INSTANCE.reviewToReviewResponseDto(review);
    }

    @Transactional
    public ReviewResponseDto.Update deleteReview(Long bookId, Long reviewId, String jwt) throws JsonProcessingException {
        Review review = reviewRepository.findByIdAndIsDeleted(reviewId, false).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        String identifier = jwtUtils.getIdentifierFromJwt(jwt);

        if(!review.getIdentifier().equals(identifier)) {
            throw new InvalidReviewIdentifierException(INVALID_REVIEW_IDENTIFIER);
        }

        review.delete();

        ResponseEntity<String> response
                = restTemplate.exchange(REVIEW_IN_BOOK_URL + bookId + "/reviews",
                HttpMethod.PUT,
                new HttpEntity<>(BookRequestDto.builder().rating(review.getRating()).build(),
                jwtUtils.getHttpHeadersIncludedJwt(jwt)), String.class);

        return new ObjectMapper().readValue(response.getBody(), ReviewResponseDto.Update.class);
    }

    private List<ReviewResponseDto.List> getReviewResponseDtoList(Page<Review> reviewPage) {

        return reviewPage.stream().map(ReviewMapper.INSTANCE::reviewToReviewResponseDto).collect(Collectors.toList());
    }

}
