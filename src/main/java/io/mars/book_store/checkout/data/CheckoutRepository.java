package io.mars.book_store.checkout.data;

import io.mars.book_store.checkout.model.entity.Checkout;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CheckoutRepository extends CrudRepository<Checkout, Long>, JpaSpecificationExecutor<Checkout> {

    @Query(value = "SELECT ch FROM Checkout ch LEFT JOIN ch.customer c LEFT JOIN ch.book b " +
            "WHERE c.nationalCode=:nationalCode AND b.isbn=:isbn AND ch.refunded = false")
    Optional<Checkout> findByCustomerAndBook(String nationalCode, String isbn);

    Optional<Checkout> findByCheckoutNumber(String checkoutNumber);

    @Query(value = "SELECT ch FROM Checkout ch WHERE ch.refunded = false")
    List<Checkout> findAllInCheck();
}
