package io.mars.book_store.book.model.entity;

import io.mars.book_store.base.BaseEntity;
import io.mars.book_store.paritipator.model.entity.Participator;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "book",
uniqueConstraints = {
        @UniqueConstraint(columnNames = "isbn", name = "book_isbn_unique_key")
})
public class Book extends BaseEntity {

    private String title;
    private String isbn;
    private LocalDate publishDate;


    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(name = "book_writers",
        joinColumns = {@JoinColumn(
                name = "book_id",
                referencedColumnName = "id",
                foreignKey = @ForeignKey(name = "book_writers_book_fk_id"))},
        inverseJoinColumns = {@JoinColumn(
                name = "writer_id",
                referencedColumnName = "id",
                foreignKey = @ForeignKey(name = "book_writers_writer_fk_id")
        )})
    private List<Participator> writers;

    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(name = "book_translators",
            joinColumns = {@JoinColumn(
                    name = "book_id",
                    referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "book_translators_book_fk_id"))},
            inverseJoinColumns = {@JoinColumn(
                    name = "translator_id",
                    referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "book_translators_translator_fk_id")
            )})
    private List<Participator> translators;


    private int publishedYear;

    @Min(0)
    private int availableCount;
    private Integer publishSet;

    public void incrementAvailableCount(){
        ++ availableCount;
    }

    public boolean decrementAvailableCount(){
        if (availableCount -1 < 0) return false;
        -- availableCount;
        return true;
    }
}
