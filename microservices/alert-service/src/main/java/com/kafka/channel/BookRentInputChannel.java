package com.kafka.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface BookRentInputChannel {

    String BOOK_RENT_ALERT_CONSUMER = "book-rent-alert-consumer";
    String BOOK_RENT_MAIL_CONSUMER = "book-rent-mail-consumer";


    @Input(BOOK_RENT_ALERT_CONSUMER)
    SubscribableChannel inputAlertChannel();

    @Input(BOOK_RENT_MAIL_CONSUMER)
    SubscribableChannel inputMailChannel();

}
