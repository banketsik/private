package org.notification_service;

import org.junit.jupiter.api.Test;

import org.notification_service.event.UserEvent;
import org.notification_service.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.time.LocalDateTime;




@SpringBootTest
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
public class UserEventListenerIntegrationTest {

    @Autowired
    private KafkaTemplate<String, UserEvent> kafkaTemplate;

    @MockBean
    private EmailService emailService;

    @Test
    public void testUserCreationEventProcessing() throws InterruptedException {
        UserEvent event = new UserEvent(
                "CREATE",
                "newuser@example.com",
                123L,
                LocalDateTime.now()
        );

        kafkaTemplate.send("user-events", String.valueOf(event.getUserId()), event);

        Thread.sleep(2000); // Wait for message processing

        verify(emailService, times(1)).sendUserCreationEmail("newuser@example.com");
    }

    @Test
    public void testUserDeletionEventProcessing() throws InterruptedException {
        UserEvent event = new UserEvent(
                "DELETE",
                "deleteuser@example.com",
                456L,
                LocalDateTime.now()
        );

        kafkaTemplate.send("user-events", String.valueOf(event.getUserId()), event);

        Thread.sleep(2000);

        verify(emailService, times(1)).sendUserDeletionEmail("deleteuser@example.com");
    }
}