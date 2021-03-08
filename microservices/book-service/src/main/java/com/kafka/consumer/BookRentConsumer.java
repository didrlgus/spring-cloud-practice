package com.kafka.consumer;

import com.domain.book.Book;
import com.domain.book.BookRepository;
import com.domain.rent.Rent;
import com.domain.rent.RentRepository;
import com.exception.EntityNotFoundException;
import com.kafka.message.BookRentMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.exception.message.CommonExceptionMessage.ENTITY_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class BookRentConsumer {

    private final BookRepository bookRepository;
    private final RentRepository rentRepository;

    /**
     * add rent and update book consumer
     */
    @Transactional
    public void addRentAndUpdateBook(BookRentMessage message) {
        Book book = bookRepository.findByIdAndIsDeleted(message.getBookId(), false).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        Rent rent = rentRepository.save(book.toRent(message.getIdentifier(), message.getRentExpiredDate()));

        book.rent(rent.getIdentifier(), rent.getId());
    }

}
