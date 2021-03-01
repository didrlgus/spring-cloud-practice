package com.service;

import com.domain.Book;
import com.domain.BookRepository;
import com.dto.BookPagingResponseDto;
import com.dto.BookRequestDto;
import com.dto.BookResponseDto;
import com.exception.*;
import com.utils.page.PageResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.exception.message.BookExceptionMessage.*;
import static com.exception.message.CommonExceptionMessage.ENTITY_NOT_FOUND;
import static java.util.Objects.isNull;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    private static final int MIN_PAGE_VAL = 1;
    private static final int BOOK_PAGE_SIZE = 10;
    private static final int BOOK_SCALE_SIZE = 10;

    @Transactional
    public BookResponseDto addBook(BookRequestDto.Post bookRequestDto) {

        return modelMapper.map(bookRepository.save(bookRequestDto.toEntity()), BookResponseDto.class);
    }

    public BookPagingResponseDto getBooks(Integer page) {
        if (page < MIN_PAGE_VAL) {
            throw new InvalidPageValueException(INVALID_PAGE_VALUE);
        }

        Pageable pageable = PageRequest.of(getRealPage(page), BOOK_PAGE_SIZE, Sort.by(Sort.Direction.DESC, "createdDate"));

        Page<Book> bookPage = bookRepository.findAllByIsDeleted(false, pageable);

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

        return modelMapper.map(book, BookResponseDto.class);
    }

    @Transactional
    public BookResponseDto returnBook(Long id, String identifier) {
        Book book = bookRepository.findByIdAndIsDeleted(id, false).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        if (isNull(book.getIdentifier()) || !book.getIdentifier().equals(identifier)) {
            throw new InvalidIdentifierException(INVALID_IDENTIFIER_VALUE);
        }

        book.returnBook();

        return modelMapper.map(book, BookResponseDto.class);
    }

    @Transactional
    public BookResponseDto extendRent(Long id, String identifier) {
        Book book = bookRepository.findByIdAndIsDeleted(id, false).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        if (isNull(book.getIdentifier()) || !book.getIdentifier().equals(identifier)) {
            throw new InvalidIdentifierException(INVALID_IDENTIFIER_VALUE);
        }

        if(book.getExtensionCount() >= 3) {
            throw new ExtensionCountException(INVALID_EXTENSION_COUNT_VALUE);
        }

        book.extendRent();

        return modelMapper.map(book, BookResponseDto.class);
    }

    private List<BookResponseDto> getBookResponseDtoList(Page<Book> bookPage) {
        return bookPage.stream().map(Book -> modelMapper.map(Book, BookResponseDto.class)).collect(Collectors.toList());
    }

    private BookPagingResponseDto getBookPagingResponseDto(Page<Book> bookPage, List<BookResponseDto> bookResponseDtoList) {
        PageResponseData pageResponseData = new PageResponseData();
        pageResponseData.setPagingInfo(bookPage.getNumber(), bookPage.getTotalPages(), BOOK_SCALE_SIZE);

        return BookPagingResponseDto.builder()
                .bookResponseDtoList(bookResponseDtoList)
                .pageResponseData(pageResponseData)
                .build();
    }

    private int getRealPage(Integer page) {
        return isNull(page) ? 0 : page - 1;
    }

}
