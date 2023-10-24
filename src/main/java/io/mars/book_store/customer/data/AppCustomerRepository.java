package io.mars.book_store.customer.data;

import io.mars.book_store.customer.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AppCustomerRepository extends CrudRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    Optional<Customer> findByNationalCode(String nationalCode);
}
