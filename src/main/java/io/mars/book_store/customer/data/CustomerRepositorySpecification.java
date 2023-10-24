package io.mars.book_store.customer.data;

import io.mars.book_store.common.exception.ContentNotFoundException;
import io.mars.book_store.customer.model.entity.Customer;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepositorySpecification {

    public static Specification<Customer> name(String name){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("name"), name));
    }

    public static Specification<Customer> lastName(String lastName){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("name"), lastName));
    }

    public static Specification<Customer> membership(LocalDate date){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("membershipDate"), date));
    }

    public static Specification<Customer> findCustomersBy(String name, String lastName, String membershipDate) {

        List<Specification<Customer>> specList = new ArrayList<>();

        if (name != null) specList.add(name(name));
        if (lastName != null) specList.add(lastName(lastName));
        if (membershipDate != null) specList.add(membership(LocalDate.parse(membershipDate)));

        return specList.stream()
                .reduce(Specification::and)
                .orElseThrow(() -> new ContentNotFoundException("no specification has provided"));
    }
}
