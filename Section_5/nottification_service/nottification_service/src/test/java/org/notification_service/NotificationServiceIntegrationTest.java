package org.notification_service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import org.notification_service.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@Testcontainers
public class EmailServiceIntegrationTest {

    @Container
    static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.4.0"));

    @Autowired
    private EmailService emailService;

    @Autowired
    private JavaMailSender mailSender;

    @MockBean
    private JavaMailSender mockMailSender;

    @Test
    public void testSendUserCreationEmail() {
        String email = "test@example.com";

        emailService.sendUserCreationEmail(email);

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mockMailSender).send(captor.capture());

        SimpleMailMessage sentMessage = captor.getValue();
        assertEquals(email, sentMessage.getTo()[0]);
        assertTrue(sentMessage.getText().contains("аккаунт на сайте"));
    }

    @Test
    public void testSendUserDeletionEmail() {
        String email = "test@example.com";

        emailService.sendUserDeletionEmail(email);

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mockMailSender).send(captor.capture());

        SimpleMailMessage sentMessage = captor.getValue();
        assertEquals(email, sentMessage.getTo()[0]);
        assertTrue(sentMessage.getText().contains("аккаунт был удалён"));
    }
}
