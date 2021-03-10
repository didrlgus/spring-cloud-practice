//package com.kafka.consumer;
//
//import com.common.MailUtils;
//import com.domain.Alert;
//import com.domain.AlertRepository;
//import com.kafka.message.BookRentMessage;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.Context;
//
//import javax.mail.internet.MimeMessage;
//
//import static com.common.MailUtils.BOOK_RENT_ALERT_MAIL_TEMPLATES_PATH;
//import static com.common.MailUtils.MAIL_TEMPLATES_PREFIX_PATH;
//
//@Slf4j
//@RequiredArgsConstructor
//@Service
//public class BookRentConsumer {
//
//    private final AlertRepository alertRepository;
//    private final JavaMailSender javaMailSender;
//    private final TemplateEngine templateEngine;
//    private final MailUtils mailUtils;
//
//    /**
//     * add alert data about book rent event
//     */
//    @Transactional
//    public void addBookRentAlert(BookRentMessage message) {
//
//        alertRepository.save(Alert.builder()
//                .identifier(message.getIdentifier())
//                .email(message.getEmail())
//                .title("gabia library ["  + message.getAlertType().value() + "] 알람 메일입니다.")
//                .message("도서대여 알람 메시지 입니다.")
//                .alertType(message.getAlertType())
//                .build());
//    }
//
//    /**
//     * send mail about book rent event
//     */
//    public void sendBookRentMail(BookRentMessage message) throws Exception {
//        Context context = getBookRentMailContext(message);
//
//        String mailString = templateEngine.process(MAIL_TEMPLATES_PREFIX_PATH + BOOK_RENT_ALERT_MAIL_TEMPLATES_PATH, context);
//
//        MimeMessage mimeMessage = mailUtils.getMimeMessage(message.getEmail(), message.getAlertType().value(), mailString, javaMailSender);
//
//        javaMailSender.send(mimeMessage);
//
//        log.info("메일 전송에 성공했습니다.");
//    }
//
//    private Context getBookRentMailContext(BookRentMessage message) {
//        Context context = new Context();
//        context.setVariable("identifier", message.getIdentifier());
//        context.setVariable("message", "해당 도서를 대여하셨습니다.");
//        context.setVariable("bookTitle", message.getBookTitle());
//        context.setVariable("bookAuthor", message.getBookAuthor());
//        context.setVariable("rentExpiredDate", message.getRentExpiredDate());
//
//        return context;
//    }
//
//}
