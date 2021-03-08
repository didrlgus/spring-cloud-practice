package com.listener;

import com.kafka.BookRentMessage;
import com.kafka.channel.BookRentInputChannel;
import com.kafka.consumer.BookRentConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@RequiredArgsConstructor
@EnableBinding({BookRentInputChannel.class})
public class BookRentStreamListener {

    private final BookRentConsumer bookRentConsumer;

    /**
     * book rent stream listener
     */
    @StreamListener(BookRentInputChannel.BOOK_RENT_CONSUMER)
    public void bookRentListener(BookRentMessage message) {
        bookRentConsumer.addRentAndUpdateBook(message);
    }

}
