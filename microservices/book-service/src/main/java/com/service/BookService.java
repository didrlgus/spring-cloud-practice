package com.service;

import com.common.error.exception.EntityNotFoundException;
import com.common.error.exception.InvalidValueException;
import com.domain.Book;
import com.domain.BookRepository;
import com.dto.BookPagingResponseDto;
import com.dto.BookRequestDto;
import com.dto.BookResponseDto;
import com.dto.PageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public BookResponseDto addBook(BookRequestDto.Post bookRequestDto) {

        return modelMapper.map(bookRepository.save(bookRequestDto.toEntity()), BookResponseDto.class);
    }

    public BookPagingResponseDto getBooks(Integer page) {
        // TODO: page는 1이상, 그렇지 않으면 예외
        if (page < 1) {
            throw new InvalidValueException("유효하지 않은 페이지 값입니다.");
        }

        Pageable pageable = PageRequest.of(getRealPage(page), 10, Sort.by(Sort.Direction.DESC, "createdDate"));

        Page<Book> bookPage = bookRepository.findAllByIsDeleted(false, pageable);

        return getBookPagingResponseDto(bookPage, getBookResponseDtoList(bookPage));
    }

    public BookResponseDto getBookDetails(Long id) {

        return modelMapper.map(bookRepository.findByIdAndIsDeleted(id, false).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 책입니다.")),
                BookResponseDto.class);
    }

    @Transactional
    public BookResponseDto updateBook(Long id, BookRequestDto.Put bookRequestDto) {
        Book book = bookRepository.findByIdAndIsDeleted(id, false).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 책입니다."));

        book.update(bookRequestDto);

        return modelMapper.map(book, BookResponseDto.class);
    }

    @Transactional
    public BookResponseDto deleteBook(Long id) {
        Book book = bookRepository.findByIdAndIsDeleted(id, false).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 책입니다."));

        book.delete();

        return modelMapper.map(book, BookResponseDto.class);
    }

    private List<BookResponseDto> getBookResponseDtoList(Page<Book> bookPage) {
        return bookPage.stream().map(Book -> modelMapper.map(Book, BookResponseDto.class)).collect(Collectors.toList());
    }

    private BookPagingResponseDto getBookPagingResponseDto(Page<Book> bookPage, List<BookResponseDto> bookResponseDtoList) {
        PageUtils pageUtils = new PageUtils();
        pageUtils.setPagingInfo(bookPage.getNumber(), bookPage.getTotalPages(), 10);

        return BookPagingResponseDto.builder()
                .bookResponseDtoList(bookResponseDtoList)
                .pageUtils(pageUtils)
                .build();
    }

    private int getRealPage(Integer page) {
        return isNull(page) ? 0 : page - 1;
    }
}
