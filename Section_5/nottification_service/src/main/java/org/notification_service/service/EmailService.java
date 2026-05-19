package org.notification_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public void sendUserCreationEmail(String email) {
        sendEmail(
                email,
                "Аккаунт создан",
                "Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан."
        );
    }

    public void sendUserDeletionEmail(String email) {
        sendEmail(
                email,
                "Аккаунт удалён",
                "Здравствуйте! Ваш аккаунт был удалён."
        );
    }
}