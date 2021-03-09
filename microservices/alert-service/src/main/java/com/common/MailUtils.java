package com.common;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailUtils {

    public final static String MAIL_TEMPLATES_PREFIX_PATH = "mail/";
    public final static String BOOK_RENT_ALERT_MAIL_TEMPLATES_PATH = "book-rent-alert-mail";
    public final static String BOOK_RETURN_ALERT_MAIL_TEMPLATES_PATH = "book-return-alert-mail";

    private final static String MAIL_SENDER = "no-reply@gabia-library.com";

    public MimeMessage getMimeMessage(String toEmail, String alertTypeValue, String mailString, JavaMailSender javaMailSender) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
        mimeMessageHelper.setFrom(MAIL_SENDER);
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setSubject("gabia library ["  + alertTypeValue + "] 알람 메일입니다.");
        mimeMessageHelper.setText(mailString, true);

        return mimeMessage;
    }

}
