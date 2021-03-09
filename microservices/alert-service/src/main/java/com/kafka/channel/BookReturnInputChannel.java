package com.kafka.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface BookReturnInputChannel {

    String BOOK_RETURN_ALERT_CONSUMER = "book-return-alert-consumer";
    String BOOK_RETURN_MAIL_CONSUMER = "book-return-mail-consumer";


    @Input(BOOK_RETURN_ALERT_CONSUMER)
    SubscribableChannel inputAlertChannel();

    @Input(BOOK_RETURN_MAIL_CONSUMER)
    SubscribableChannel inputMailChannel();

}
