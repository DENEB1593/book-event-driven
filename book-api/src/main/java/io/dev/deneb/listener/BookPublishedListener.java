package io.dev.deneb.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dev.deneb.exception.InvalidMessageException;
import io.dev.deneb.model.Book;
import io.dev.deneb.model.Notification;
import io.dev.deneb.service.BooksService;
import io.dev.deneb.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@AllArgsConstructor
public class BookPublishedListener {

    private final ObjectMapper objectMapper;
    private final BooksService booksService;
    private final NotificationService notificationService;

    @KafkaListener(topics = "books.published")
    public String listens(String in) {
        log.info("Received Book: {}", in);
        try {
            Book book = parseBook(in);
            Book savedBook = booksService.save(book);

            String message = String.format(
                    "Book '%s' [%s] persisted!",
                    savedBook.getTitle(),
                    savedBook.getIsbn()
            );
            notificationService.publishNotification(
                    Notification.builder()
                            .message(message)
                            .timestamp(LocalDateTime.now())
                            .service("book-persistence")
                            .build());

        } catch (Exception ex) {
            log.error("Invalid message received: {}", in);
        }


        return in;
    }

    private Book parseBook(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, Book.class);
    }

}
