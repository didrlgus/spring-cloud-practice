package com.kafka.consumer;

import com.domain.Alert;
import com.domain.AlertRepository;
import com.kafka.BookRentMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookRentConsumer {

    private final AlertRepository alertRepository;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    /**
     * add alert data about book rent event
     */
    @Transactional
    public void addBookRentAlert(BookRentMessage message) {

        alertRepository.save(Alert.builder()
                .identifier(message.getIdentifier())
                .email(message.getEmail())
                .title("gabia library ["  + message.getAlertType().value() + "] 알람 메일입니다.")
                .message("test 알람 메시지 입니다.")
                .alertType(message.getAlertType())
                .build());
    }

    /**
     * send mail about book rent event
     */
    public void sendBookRentMail(BookRentMessage message) throws Exception {
        Context context = getBookRentMailContext(message);

        String mailString = templateEngine.process("mail/book-rent-alert-mail", context);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
        mimeMessageHelper.setTo(message.getEmail());
        mimeMessageHelper.setSubject("gabia library ["  + message.getAlertType().value() + "] 알람 메일입니다.");
        mimeMessageHelper.setText(mailString, true);

        javaMailSender.send(mimeMessage);

        log.info("메일 전송에 성공했습니다.");
    }

    private Context getBookRentMailContext(BookRentMessage message) {
        Context context = new Context();
        context.setVariable("identifier", message.getIdentifier());
        context.setVariable("bookTitle", message.getBookTitle());
        context.setVariable("bookAuthor", message.getBookAuthor());
        context.setVariable("message", "해당 도서를 대여하셨습니다.");

        return context;
    }

}
