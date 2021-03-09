package com.kafka.publisher;

import com.kafka.BookRentMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaBookRentMessageSender {

    @Qualifier("bookRentKafkaTemplate")
    private final KafkaTemplate<String, BookRentMessage> kafkaTemplate;

    @Value("${kafka.bookRentTopicName}")
    private String topicName;

    public void send(BookRentMessage bookRentMessage) {

        Message<BookRentMessage> message = MessageBuilder
                .withPayload(bookRentMessage)
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .build();

        ListenableFuture<SendResult<String, BookRentMessage>> future = kafkaTemplate.send(message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, BookRentMessage>>() {
            @Override
            public void onSuccess(SendResult<String, BookRentMessage> stringObjectSendResult) {
                System.out.println("Sent message=[" + stringObjectSendResult.getProducerRecord().value().toString() +
                        "] with offset=[" + stringObjectSendResult.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                log.info("Unable to send message=[] due to : " + ex.getMessage());
            }
        });
    }
}
