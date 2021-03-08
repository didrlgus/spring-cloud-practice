package com.listener;

import com.kafka.channel.BookRentInputChannel;
import com.kafka.consumer.BookRentConsumer;
import com.kafka.message.BookRentMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import javax.mail.MessagingException;

@RequiredArgsConstructor
@EnableBinding({BookRentInputChannel.class})
public class BookRentStreamListener {

    private final BookRentConsumer bookRentConsumer;

    @StreamListener(BookRentInputChannel.BOOK_RENT_ALERT_CONSUMER)
    public void bookRentAlertListener(BookRentMessage message) { bookRentConsumer.addBookRentAlert(message); }

    @StreamListener(BookRentInputChannel.BOOK_RENT_MAIL_CONSUMER)
    public void bookRentMailListener(BookRentMessage message) throws Exception { bookRentConsumer.sendBookRentMail(message); }

}
