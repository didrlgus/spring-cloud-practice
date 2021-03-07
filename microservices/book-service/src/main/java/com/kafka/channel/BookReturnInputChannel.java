package com.kafka.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface BookReturnInputChannel {

    String BOOK_RETURN_CONSUMER = "book-return-consumer";
    String RENT_RETURN_CONSUMER = "rent-return-consumer";

    @Input(BOOK_RETURN_CONSUMER)
    SubscribableChannel bookReturnInputChannel();

    @Input(RENT_RETURN_CONSUMER)
    SubscribableChannel rentReturnInputChannel();

}
