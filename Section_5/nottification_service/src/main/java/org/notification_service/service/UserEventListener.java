package org.notification_service.service;

import lombok.RequiredArgsConstructor;
import org.notification_service.event.UserEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEventListener {
    private final EmailService emailService;

    @KafkaListener(topics = "${kafka.topic.user-events}", groupId = "notification-service")
    public void handleUserEvent(UserEvent event) {
        if ("CREATE".equals(event.getEventType())) {
            emailService.sendUserCreationEmail(event.getEmail());
        } else if ("DELETE".equals(event.getEventType())) {
            emailService.sendUserDeletionEmail(event.getEmail());
        }
    }
}
