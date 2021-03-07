package com.service;

import com.domain.book.Book;
import com.domain.book.BookAlertType;
import com.domain.book.BookRepository;
import com.domain.rent.Rent;
import com.domain.rent.RentRepository;
import com.dto.BookResponseDto;
import com.dto.RentResponseDto;
import com.exception.*;
import com.kafka.channel.BookRentOutputChannel;
import com.kafka.channel.BookReturnOutputChannel;
import com.kafka.message.BookReturnMessage;
import com.kafka.publisher.MessagePublisher;
import com.mapper.BookMapper;
import com.mapper.RentMapper;
import com.utils.page.PageUtils;
import com.utils.page.PagingResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.exception.message.BookExceptionMessage.*;
import static com.exception.message.CommonExceptionMessage.ENTITY_NOT_FOUND;

@EnableBinding({BookRentOutputChannel.class, BookReturnOutputChannel.class})
@Slf4j
@RequiredArgsConstructor
@Service
public class RentService {

    private final BookRepository bookRepository;
    private final RentRepository rentRepository;
    private final PageUtils pageUtils;
    private final MessagePublisher messagePublisher;

    private static final int RENT_PAGE_SIZE = 10;
    private static final int RENT_SCALE_SIZE = 10;

    @Transactional
    public RentResponseDto rentBook(Long id, String identifier) {
        Book book = bookRepository.findByIdAndIsDeleted(id, false).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        if (book.isRent()) {
            throw new AlreadyRentException(ALREADY_RENT);
        }

        /**
         * send event to kafka
         */
        messagePublisher.publishBookRentMessage(book.toBookRentMessage(identifier));

        return RentMapper.INSTANCE.bookToRentResponseDto(book);
    }

    @Transactional
    public BookResponseDto extendRent(Long bookId, Long rentId, String identifier) {
        Book book = bookRepository.findByIdAndIsDeleted(bookId, false).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        if (isInValidIdentifier(book.getIdentifier(), identifier)) {
            throw new InvalidIdentifierException(INVALID_IDENTIFIER_VALUE);
        }

        if (book.getExtensionCount() >= 3) {
            throw new ExtensionCountException(INVALID_EXTENSION_COUNT_VALUE);
        }

        book.extendRent();

        Rent rent = rentRepository.findById(rentId).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        if (rent.isInvalidStatus()) {
            throw new InvalidRentStatusException(INVALID_RENT_STATUS_EXCEPTION);
        }

        rent.extendRent(book);

        return BookMapper.INSTANCE.bookToBookAndRentResponseDto(book, rent);
    }

    @Transactional
    public BookResponseDto returnBook(Long bookId, Long rentId, String identifier) {
        Book book = bookRepository.findByIdAndIsDeleted(bookId, false).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        if (isInValidIdentifier(book.getIdentifier(), identifier)) {
            throw new InvalidIdentifierException(INVALID_IDENTIFIER_VALUE);
        }

        Rent rent = rentRepository.findById(rentId).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        /**
         * send event to kafka
         */
        messagePublisher.publishBookReturnMessage(BookReturnMessage.builder()
                .bookId(bookId)
                .rentId(rentId)
                .identifier(identifier)
                .bookAlertType(BookAlertType.RETURN)
                .build());

        return BookMapper.INSTANCE.bookToBookAndRentResponseDto(book, rent);
    }

    public PagingResponseDto getRentListOfUser(String identifier, Integer page) {

        Page<Rent> rentPage = rentRepository.findAllByIdentifier(identifier, pageUtils.getPageable(page, RENT_PAGE_SIZE, Sort.Direction.DESC, "createdDate"));

        List<RentResponseDto> rentResponseDtoList = rentPage.stream().map(RentMapper.INSTANCE::rentToRentResponseDto).collect(Collectors.toList());

        return pageUtils.getCommonPagingResponseDto(rentPage, rentResponseDtoList, RENT_SCALE_SIZE);
    }

    private boolean isInValidIdentifier(String identifierFromEntity, String identifier) {
        return Optional.ofNullable(identifierFromEntity)
                .map(val -> !val.equals(identifier))
                .orElse(true);
    }

}
