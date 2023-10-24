package io.mars.book_store.book.service;

import io.mars.book_store.book.data.BookRepository;
import io.mars.book_store.book.model.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.mars.book_store.book.data.BookRepositorySpecification.*;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @Override
    public List<Book> works(String name) {
        List<Book> writer = bookRepository.findAll(writer(name));
        List<Book> translator = bookRepository.findAll(translator(name));
        List<Book> result = new ArrayList<>();
        result.addAll(translator);
        result.addAll(writer);
        return result;
    }

    @Override
    public List<Book> find(String title, String writer, String translator, String publish) {
        Specification<Book> specification = findBooksBy(title, writer, translator, publish);
        return bookRepository.findAll(specification);
}
}
