package com.service;

import com.domain.*;
import com.dto.*;
import com.exception.*;
import com.mapper.BookMapper;
import com.utils.page.PageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.exception.message.BookExceptionMessage.*;
import static com.exception.message.CommonExceptionMessage.ENTITY_NOT_FOUND;
import static com.exception.message.CommonExceptionMessage.INVALID_PAGE_VALUE;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final RentRepository rentRepository;
    private final ModelMapper modelMapper;
    private final PageUtils pageUtils;

    private static final int BOOK_PAGE_SIZE = 10;
    private static final int BOOK_SCALE_SIZE = 10;

    @Transactional
    public BookResponseDto addBook(BookRequestDto.Post bookRequestDto) {

        return modelMapper.map(bookRepository.save(bookRequestDto.toEntity()), BookResponseDto.class);
    }

    public BookPagingResponseDto getBooks(Integer page) {
        if (pageUtils.isInvalidPageValue(page)) {
            throw new InvalidPageValueException(INVALID_PAGE_VALUE);
        }

        Page<Book> bookPage = bookRepository.findAllByIsDeleted(false,
                pageUtils.getPageable(page, BOOK_PAGE_SIZE, Sort.Direction.DESC, "createdDate"));

        return getBookPagingResponseDto(bookPage, getBookResponseDtoList(bookPage));
    }

    public BookResponseDto getBookDetails(Long id) {

        return modelMapper.map(bookRepository.findByIdAndIsDeleted(id, false).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND)),
                BookResponseDto.class);
    }

    @Transactional
    public BookResponseDto updateBook(Long id, BookRequestDto.Put bookRequestDto) {
        Book book = bookRepository.findByIdAndIsDeleted(id, false).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        book.update(bookRequestDto);

        return modelMapper.map(book, BookResponseDto.class);
    }

    @Transactional
    public BookResponseDto deleteBook(Long id) {
        Book book = bookRepository.findByIdAndIsDeleted(id, false).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        book.delete();

        return modelMapper.map(book, BookResponseDto.class);
    }

    @Transactional
    public BookResponseDto rentBook(Long id, String identifier) {
        Book book = bookRepository.findByIdAndIsDeleted(id, false).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        if (book.isRent()) {
            throw new AlreadyRentException(ALREADY_RENT);
        }

        book.rent(identifier);

        Rent rent = rentRepository.save(Rent.builder()
                .bookId(book.getId())
                .identifier(identifier)
                .rentExpiredDate(book.getRentExpiredDate())
                .rentStatus(RentStatus.RENT)
                .build());

        BookResponseDto ret = BookMapper.INSTANCE.bookToBookAndRentResponseDto(book, rent);

        return modelMapper.map(book, BookResponseDto.class);
    }

    @Transactional
    public BookResponseDto extendRent(Long id, String identifier) {
        Book book = bookRepository.findByIdAndIsDeleted(id, false).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        if (isInValidIdentifier(book.getIdentifier(), identifier)) {
            throw new InvalidIdentifierException(INVALID_IDENTIFIER_VALUE);
        }

        if(book.getExtensionCount() >= 3) {
            throw new ExtensionCountException(INVALID_EXTENSION_COUNT_VALUE);
        }

        book.extendRent();

        return modelMapper.map(book, BookResponseDto.class);
    }

    @Transactional
    public BookResponseDto returnBook(Long id, String identifier) {
        Book book = bookRepository.findByIdAndIsDeleted(id, false).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        if (isInValidIdentifier(book.getIdentifier(), identifier)) {
            throw new InvalidIdentifierException(INVALID_IDENTIFIER_VALUE);
        }

        book.returnBook();

        return modelMapper.map(book, BookResponseDto.class);
    }

    private List<BookResponseDto> getBookResponseDtoList(Page<Book> bookPage) {
        return bookPage.stream().map(book -> {
            BookResponseDto bookResponseDto = BookMapper.INSTANCE.bookToBookResponseDto(book);
            bookResponseDto.setAvgReviewRating(book.calcAvgReviewRating());

            return bookResponseDto;
        }).collect(Collectors.toList());
    }

    private BookPagingResponseDto getBookPagingResponseDto(Page<Book> bookPage, List<BookResponseDto> bookResponseDtoList) {

        return BookPagingResponseDto.builder()
                .bookResponseDtoList(bookResponseDtoList)
                .pageResponseData(pageUtils.getPageResponseData(bookPage, BOOK_SCALE_SIZE))
                .build();
    }

    private boolean isInValidIdentifier(String identifierFromEntity, String identifier) {
        return Optional.ofNullable(identifierFromEntity)
                .map(val -> !val.equals(identifier))
                .orElse(true);
    }

    @Transactional
    public ReviewResponseDto addReviewRating(Long bookId, ReviewRequestDto reviewRequestDto, String reviewIdentifier) {
        Book book = bookRepository.findByIdAndIsDeleted(bookId, false).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        book.addReviewRating(reviewRequestDto);

        return book.toReviewResponseDto(reviewIdentifier);
    }
}
