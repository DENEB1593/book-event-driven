package io.dev.deneb.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dev.deneb.config.KafkaConfig;
import io.dev.deneb.config.KafkaProperties;
import io.dev.deneb.model.Notification;
import io.dev.deneb.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaNotificationService implements NotificationService {

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final KafkaProperties kafkaProperties;


    @Override
    public void publishNotification(Notification notification) {
        try {
            String payload = objectMapper.writeValueAsString(notification);
            kafkaTemplate.send(kafkaProperties.getTopic(), payload);
        } catch(final JsonProcessingException ex) {
            throw new RuntimeException("Unable to publish notification", ex);
        }
    }
}
