package com.service.consumer;

import com.domain.book.Book;
import com.domain.book.BookRepository;
import com.domain.rent.Rent;
import com.domain.rent.RentRepository;
import com.exception.EntityNotFoundException;
import com.kafka.message.BookReturnMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.exception.message.CommonExceptionMessage.ENTITY_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class ReturnConsumerService {

    private final BookRepository bookRepository;
    private final RentRepository rentRepository;

    @Transactional
    public void bookReturn(BookReturnMessage message) {
        Book book = bookRepository.findByIdAndIsDeleted(message.getBookId(), false).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        book.returnBook();
    }

    @Transactional
    public void rentReturn(BookReturnMessage message) {
        Rent rent = rentRepository.findById(message.getRentId()).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        rent.returnBook();
    }

}
