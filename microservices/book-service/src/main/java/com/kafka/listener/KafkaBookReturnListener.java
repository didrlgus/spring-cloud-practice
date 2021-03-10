package com.kafka.listener;

import com.domain.book.Book;
import com.domain.book.BookRepository;
import com.domain.rent.Rent;
import com.domain.rent.RentRepository;
import com.exception.EntityNotFoundException;
import com.kafka.message.BookReturnMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.exception.message.CommonExceptionMessage.ENTITY_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class KafkaBookReturnListener {

    private final BookRepository bookRepository;
    private final RentRepository rentRepository;

    @Transactional
    @KafkaListener(topics = "${kafka.topic.return.name}",
            groupId = "${kafka.consumer.return.groupName}",
            containerFactory = "kafkaBookReturnListenerContainerFactory")
    public void bookReturn(@Payload BookReturnMessage message,
                                     @Headers MessageHeaders messageHeaders) {

        Book book = bookRepository.findByIdAndIsDeleted(message.getBookId(), false).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        book.returnBook();

        Rent rent = rentRepository.findById(message.getRentId()).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        rent.returnBook();
    }

}
