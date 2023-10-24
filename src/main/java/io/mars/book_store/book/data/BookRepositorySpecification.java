package io.mars.book_store.book.data;

import io.mars.book_store.book.model.entity.Book;
import io.mars.book_store.common.exception.ContentNotFoundException;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookRepositorySpecification {

    public static Specification<Book> writer(String name){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("writers").get("name"), name));
    }

    public static Specification<Book> translator(String name){
        return ((root, query, cb) ->
                cb.equal(root.join("translators").get("name"), name));
    }

    public static Specification<Book> title(String title){
        return ((root, query, cb) ->
                cb.equal(root.get("title"), title));
    }

    public static Specification<Book> publish(LocalDate date){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("publishDate"), date));
    }

    public static Specification<Book> findBooksBy(String title, String writer, String translator, String publish) {

        List<Specification<Book>> specList = new ArrayList<>();

        if (title != null) specList.add(title(title));
        if (writer != null) specList.add(writer(writer));
        if (translator != null) specList.add(translator(translator));
        if (publish != null) specList.add(publish(LocalDate.parse(publish)));

        return specList.stream()
                .reduce(Specification::and)
                .orElseThrow(() -> new ContentNotFoundException("no specification has provided"));
    }
}
