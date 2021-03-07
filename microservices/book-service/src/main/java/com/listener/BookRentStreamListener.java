package com.listener;

import com.kafka.channel.BookRentInputChannel;
import com.kafka.message.BookRentMessage;
import com.service.consumer.RentConsumerService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@RequiredArgsConstructor
@EnableBinding({BookRentInputChannel.class})
public class BookRentStreamListener {

    private final RentConsumerService rentConsumerService;

    /**
     * book rent stream listener
     */
    @StreamListener(BookRentInputChannel.BOOK_RENT_CONSUMER)
    public void bookRentListener(BookRentMessage message) {
        rentConsumerService.addRentAndUpdateBook(message);
    }

}
