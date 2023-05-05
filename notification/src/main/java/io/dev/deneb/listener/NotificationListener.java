package io.dev.deneb.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dev.deneb.model.Notification;
import io.dev.deneb.service.NotificationsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationListener {

    private final ObjectMapper objectMapper;
    private final NotificationsService notificationService;

    @KafkaListener(topics = "notification.created")
    public String listens(final String in) {
        log.info("Received Notification: {}", in);
        try {
            Notification notification = objectMapper.readValue(in, Notification.class);

            Notification savedNotification = notificationService.save(notification);

            log.info("Notification '{}' persisted!", savedNotification.getTimestamp().toString());

        } catch(final JsonProcessingException ex) {
            log.error("Invalid message received: {}", in);
        }

        return in;
    }
}

