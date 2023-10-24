package io.mars.book_store.customer.controller;

import io.mars.book_store.customer.model.entity.Customer;
import io.mars.book_store.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer/")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping(value = "save")
    public ResponseEntity<Customer> save(Customer customer) {
        return ResponseEntity.ok(customerService.save(customer));
    }

    @GetMapping(value = "find")
    public ResponseEntity<List<Customer>> find(@RequestParam(name = "name", required = false) String name,
                                               @RequestParam(name = "lastName", required = false) String lastName,
                                               @RequestParam(name = "membership", required = false) String membership) {
        return ResponseEntity.ok(customerService.find(name, lastName, membership));
    }
}
