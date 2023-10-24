package io.mars.book_store.customer.service;

import io.mars.book_store.base.BaseService;
import io.mars.book_store.customer.data.AppCustomerRepository;
import io.mars.book_store.customer.model.entity.Customer;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static io.mars.book_store.customer.data.CustomerRepositorySpecification.findCustomersBy;

@Service
public class CustomerServiceImpl extends BaseService<Customer> implements CustomerService{

    private final AppCustomerRepository customerRepository;

    protected CustomerServiceImpl(Validator validator, AppCustomerRepository customerRepository) {
        super(validator);
        this.customerRepository = customerRepository;
    }

    @Override
    public Optional<Customer> findByNationalCode(String nationalCode) {
        return customerRepository.findByNationalCode(nationalCode);
    }

    @Override
    public Customer save(Customer customer) {
        validate(customer);
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> find(String name, String lastName, String membership) {
        return customerRepository.findAll(findCustomersBy(name, lastName, membership));
    }
}
