package io.mars.book_store.book.service;

import io.mars.book_store.book.model.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book save(Book book);

    Optional<Book> findById(Long id);

    Optional<Book> findByIsbn(String isbn);

    List<Book> works(String name);
    List<Book> find(String title, String writer, String translator, String publish);

}
