package io.dev.deneb.service.impl;

import io.dev.deneb.model.Book;
import io.dev.deneb.repository.BookRepository;
import io.dev.deneb.service.BooksService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BooksServiceImpl implements BooksService {

  private final BookRepository bookRepository;

  public BooksServiceImpl(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Override
  public Book save(Book book) {
    return bookRepository.save(book);
  }

  @Override
  public Page<Book> listBooks(Pageable pageable) {
    return bookRepository.findAll(pageable);
  }
}
