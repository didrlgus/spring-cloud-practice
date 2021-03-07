package com.kafka.publisher;

import com.kafka.channel.BookRentOutputChannel;
import com.kafka.channel.BookReturnOutputChannel;
import com.kafka.message.BookRentMessage;
import com.kafka.message.BookReturnMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MessagePublisher {

    private final BookRentOutputChannel bookRentOutputChannel;
    private final BookReturnOutputChannel bookReturnOutputChannel;

    public void publishBookRentMessage(BookRentMessage message) {
        bookRentOutputChannel.outputChannel().send(MessageBuilder.withPayload(message).build());
    }

    public void publishBookReturnMessage(BookReturnMessage message) {
        bookReturnOutputChannel.outputChannel().send(MessageBuilder.withPayload(message).build());
    }

}