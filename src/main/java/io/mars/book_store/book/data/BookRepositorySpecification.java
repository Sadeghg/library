package io.mars.book_store.book.data;

import io.mars.book_store.book.model.entity.Book;
import io.mars.book_store.common.exception.ContentNotFoundException;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookRepositorySpecification {

    public static Specification<Book> base(){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.conjunction());
    }

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

    public static Specification<Book> publishAt(LocalDate date){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("publishDate"), date));
    }

    public static Specification<Book> publishBefore(LocalDate date){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("publishDate"), date));
    }

    public static Specification<Book> publishAfter(LocalDate date){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("publishDate"), date));
    }

    public static Specification<Book> findBooksBy(String title, String writer, String translator,
                                                  String publishAfter, String publishAt, String publishBefore) {

        List<Specification<Book>> specList = new ArrayList<>();

        if (title != null) specList.add(title(title));
        if (writer != null) specList.add(writer(writer));
        if (translator != null) specList.add(translator(translator));
        if (publishAt != null) specList.add(publishAt(LocalDate.parse(publishAt)));
        if (publishAfter != null) specList.add(publishAfter(LocalDate.parse(publishAfter)));
        if (publishBefore != null) specList.add(publishBefore(LocalDate.parse(publishBefore)));

        return specList.stream()
                .reduce(Specification::and)
                .orElseGet(BookRepositorySpecification::base);
    }
}
