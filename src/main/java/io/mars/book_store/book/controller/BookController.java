package io.mars.book_store.book.controller;

import io.mars.book_store.book.model.entity.Book;
import io.mars.book_store.book.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book/")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping(value = "save")
    private ResponseEntity<Book> save(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.save(book));
    }

    @GetMapping(value = "participator/works/{name}")
    private ResponseEntity<List<Book>> works(@PathVariable(name = "name") String name){
        return ResponseEntity.ok(bookService.works(name));
    }

    @GetMapping(value = "find")
    private ResponseEntity<List<Book>> find(@RequestParam(name = "title", required = false) String title,
                                            @RequestParam(name = "writer", required = false) String writer,
                                            @RequestParam(name = "translator", required = false) String translator,
                                            @RequestParam(name = "publish", required = false) String publish) {
        return ResponseEntity.ok(bookService.find(title, writer, translator, publish));
    }
}
