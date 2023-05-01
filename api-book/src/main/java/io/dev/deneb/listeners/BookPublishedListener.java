package io.dev.deneb.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dev.deneb.exception.InvalidMessageException;
import io.dev.deneb.model.Book;
import io.dev.deneb.model.Notification;
import io.dev.deneb.service.BooksService;
import io.dev.deneb.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class BookPublishedListener {

  private final ObjectMapper objectMapper;

	private final BooksService booksService;

	private final NotificationService notificationService;

	public BookPublishedListener(
		final ObjectMapper objectMapper,
		final BooksService booksService,
		final NotificationService notificationService) {
			this.objectMapper = objectMapper;
			this.booksService = booksService;
			this.notificationService = notificationService;
	}

	@KafkaListener(topics = "books.published")
	public String listens(final String in) {
		log.info("Received Book: {}", in);
		try {
			Map<String, Object> payload = readJsonAsMap(in);

			Book book = bookFromPayload(payload);
			Book savedBook = booksService.save(book);

			final String message = String.format(
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

		} catch(InvalidMessageException ex) {
			log.error("Invalid message received: {}", in);
		}


		return in;
	}

	private Map<String, Object> readJsonAsMap(final String json) {
		try{
			final TypeReference<HashMap<String,Object>> typeRef = new TypeReference<HashMap<String,Object>>() {};
			return objectMapper.readValue(json, typeRef);
		} catch(JsonProcessingException ex) {
			throw new InvalidMessageException();
		}
	}

	/**
	 * Note - There are MUCH MUCH MUCH better ways of doing this.
	 * 	      Implemented in this way for brevity.
	 */
	private Book bookFromPayload(final Map<String, Object> payload) {
		final Integer authorId = (Integer)((HashMap<String, Object>)payload.get("author")).get("id"); /* <- Don't do this in prod!!! :| */
        return Book.builder()
		.isbn(payload.get("isbn").toString())
		.title(payload.get("title").toString())
		.author(authorId.longValue())
		.build();
	}
}
