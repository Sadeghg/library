package io.mars.book_store.paritipator.data;

import io.mars.book_store.paritipator.model.entity.Participator;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ParticipatorRepository extends CrudRepository<Participator, Long>, JpaSpecificationExecutor<Participator> {
}
