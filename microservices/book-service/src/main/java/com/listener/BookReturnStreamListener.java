package com.listener;

import com.kafka.channel.BookReturnInputChannel;
import com.kafka.message.BookReturnMessage;
import com.service.consumer.ReturnConsumerService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@RequiredArgsConstructor
@EnableBinding({BookReturnInputChannel.class})
public class BookReturnStreamListener {

    private final ReturnConsumerService returnConsumerService;

    @StreamListener(BookReturnInputChannel.BOOK_RETURN_CONSUMER)
    public void bookReturnListener(BookReturnMessage message) {
        returnConsumerService.bookReturn(message);
    }

    @StreamListener(BookReturnInputChannel.RENT_RETURN_CONSUMER)
    public void rentReturnListener(BookReturnMessage message) {
        returnConsumerService.rentReturn(message);
    }

}
