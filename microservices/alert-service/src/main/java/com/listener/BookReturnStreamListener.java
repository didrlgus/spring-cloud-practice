package com.listener;

import com.kafka.BookReturnMessage;
import com.kafka.channel.BookReturnInputChannel;
import com.kafka.consumer.BookReturnConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@RequiredArgsConstructor
@EnableBinding({BookReturnInputChannel.class})
public class BookReturnStreamListener {

    private final BookReturnConsumer bookReturnConsumer;

    @StreamListener(BookReturnInputChannel.BOOK_RETURN_ALERT_CONSUMER)
    public void bookReturnAlertListener(BookReturnMessage message) { bookReturnConsumer.addBookReturnAlert(message); }

    @StreamListener(BookReturnInputChannel.BOOK_RETURN_MAIL_CONSUMER)
    public void bookReturnMailListener(BookReturnMessage message) throws Exception { bookReturnConsumer.sendBookReturnMail(message); }

}
