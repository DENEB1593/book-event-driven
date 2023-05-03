package io.dev.book.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dev.book.config.KafkaProperties;
import io.dev.book.model.Book;
import io.dev.book.service.BookPublishService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class KafkaBookPublishService implements BookPublishService {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaProperties kafkaProperties;

    @Override
    public void publish(Book book) {
        try {
            String payload = objectMapper.writeValueAsString(book);
            kafkaTemplate.send(kafkaProperties.getTopic(), payload);
        } catch (JsonProcessingException e) {
            log.error("unable to publish book : {}", book, e);
        }
    }

}