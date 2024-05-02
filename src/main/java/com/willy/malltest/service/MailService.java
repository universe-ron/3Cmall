package com.willy.malltest.service;


import jakarta.mail.MessagingException;
import org.springframework.mail.javamail.JavaMailSender;

public interface MailService {
    void sendPassword(String email, String phone);

    void sendVerifyCode(String email, String verificationCode);

    void sendFeedbackEmailCreate(Integer feedbackId, String email);

    void sendFeedbackEmailReturn(Integer feedbackId, String message) throws MessagingException;

    void setMailSender(JavaMailSender mailSender);

    void sendHtmlMail(String to, String subject, String body) throws MessagingException, MessagingException;
}
