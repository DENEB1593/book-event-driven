package io.dev.deneb.service;

import io.dev.deneb.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BooksService {
  Book save(Book book);

  Page<Book> listBooks(Pageable pageable);
}
