package io.mars.book_store.customer.service;

import io.mars.book_store.customer.model.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Optional<Customer> findByNationalCode(String username);

    Customer save(Customer customer);

    List<Customer> find(String name, String lastName, String membership);
}
