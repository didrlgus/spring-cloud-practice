package com.service;

import com.domain.book.Book;
import com.domain.book.BookRepository;
import com.dto.*;
import com.exception.*;
import com.mapper.BookMapper;
import com.utils.page.PageUtils;
import com.utils.page.PagingResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.exception.message.CommonExceptionMessage.ENTITY_NOT_FOUND;
import static com.exception.message.CommonExceptionMessage.INVALID_PAGE_VALUE;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final PageUtils pageUtils;

    private static final int BOOK_PAGE_SIZE = 12;
    private static final int BOOK_SCALE_SIZE = 10;
    private static final String FIRST_AVG_REVIEW_RATING = "0.0";

    @Transactional
    public BookResponseDto addBook(BookRequestDto.Post bookRequestDto) {
        Book book = bookRepository.save(bookRequestDto.toEntity());
        BookResponseDto bookResponseDto = BookMapper.INSTANCE.bookToBookResponseDto(book);
        bookResponseDto.setAvgReviewRating(Double.parseDouble(FIRST_AVG_REVIEW_RATING));

        return bookResponseDto;
    }

    public PagingResponseDto getBooks(Integer page) {
        if (pageUtils.isInvalidPageValue(page)) {
            throw new InvalidPageValueException(INVALID_PAGE_VALUE);
        }

        Page<Book> bookPage = bookRepository.findAllByIsDeleted(false,
                pageUtils.getPageable(page, BOOK_PAGE_SIZE, Sort.Direction.DESC, "createdDate"));

        return pageUtils.getCommonPagingResponseDto(bookPage, getBookResponseDtoList(bookPage), BOOK_SCALE_SIZE);
    }

    public BookResponseDto getBookDetails(Long id) {
        Book book = bookRepository.findByIdAndIsDeleted(id, false).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        BookResponseDto bookResponseDto = BookMapper.INSTANCE.bookToBookResponseDto(book);
        bookResponseDto.setAvgReviewRating(Double.parseDouble(book.calcAvgReviewRating()));

        return bookResponseDto;
    }

    @Transactional
    public BookResponseDto updateBook(Long id, BookRequestDto.Put bookRequestDto) {
        Book book = bookRepository.findByIdAndIsDeleted(id, false).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        book.update(bookRequestDto);

        BookResponseDto bookResponseDto = BookMapper.INSTANCE.bookToBookResponseDto(book);
        bookResponseDto.setAvgReviewRating(Double.parseDouble(book.calcAvgReviewRating()));

        return bookResponseDto;
    }

    @Transactional
    public BookResponseDto deleteBook(Long id) {
        Book book = bookRepository.findByIdAndIsDeleted(id, false).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        book.delete();

        BookResponseDto bookResponseDto = BookMapper.INSTANCE.bookToBookResponseDto(book);
        bookResponseDto.setAvgReviewRating(Double.parseDouble(book.calcAvgReviewRating()));

        return bookResponseDto;
    }

    @Transactional
    public ReviewResponseDto addReviewRating(Long bookId, ReviewRequestDto reviewRequestDto, String reviewIdentifier) {
        Book book = bookRepository.findByIdAndIsDeleted(bookId, false).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        book.addReviewRating(reviewRequestDto);

        return book.toReviewResponseDto(reviewIdentifier);
    }

    @Transactional
    public ReviewResponseDto deleteReview(Long id, ReviewRequestDto reviewRequestDto, String identifier) {
        Book book = bookRepository.findByIdAndIsDeleted(id, false).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        book.deleteReview(reviewRequestDto);

        return book.toReviewResponseDto(identifier);
    }

    private List<BookResponseDto> getBookResponseDtoList(Page<Book> bookPage) {
        return bookPage.stream().map(book -> {
            BookResponseDto bookResponseDto = BookMapper.INSTANCE.bookToBookResponseDto(book);
            bookResponseDto.setAvgReviewRating((Double.parseDouble(book.calcAvgReviewRating())));

            return bookResponseDto;
        }).collect(Collectors.toList());
    }

}
