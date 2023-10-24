package io.mars.book_store.paritipator.model.entity;

import io.mars.book_store.base.BaseEntity;
import io.mars.book_store.paritipator.model.enums.ParticipationType;
import jakarta.persistence.*;
import lombok.Data;

/**
 * the participator can be either writer or translator of a book
 */
@Data
@Entity
@Table(name = "participator",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "participation_type"}, name = "participator-unique-key")
        })
public class Participator extends BaseEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "participation_type")
    private ParticipationType participationType;
}
