package io.dev.book.schedule;

import io.dev.book.repository.BookRepository;
import io.dev.book.service.BookPublishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*
* DB에 저장된 책을 publish 한다. (20초 주기)
* */
@Slf4j
@Component
public class ScheduledBookPublish {

    private long counter;
    private final BookRepository bookRepository;
    private final BookPublishService bookPublisherService;

    public ScheduledBookPublish(BookPublishService bookPublisherService, BookRepository bookRepository) {
        resetCounter();
        this.bookPublisherService = bookPublisherService;
        this.bookRepository = bookRepository;
    }


    @Scheduled(cron = "*/20 * * * * *", zone = "Asia/Seoul")
    public void publish() {
        log.info("book publish schedule start");
        bookRepository.findById(counter).ifPresentOrElse(book -> {
            counter += 1;
            bookPublisherService.publish(book);
            log.info("Book '{}' [{}] published.", book.getTitle(), book.getIsbn());
        }, this::resetCounter);
    }

    private void resetCounter() {
        this.counter = 1;
    }

}
