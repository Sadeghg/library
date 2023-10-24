package io.mars.book_store.checkout.model.entity;

import io.mars.book_store.base.BaseEntity;
import io.mars.book_store.book.model.entity.Book;
import io.mars.book_store.checkout.service.validation.CheckoutValidDate;
import io.mars.book_store.customer.model.entity.Customer;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@CheckoutValidDate
@Table(name = "checkout",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"customer_id", "book_id", "checkout_number","refunded"}, name = "checkout_book_customer_unique_key"),
                @UniqueConstraint(columnNames = {"checkout_number"}, name = "checkout_number_unique_key"),
        })
public class Checkout extends BaseEntity {

    public Checkout(Customer customer, Book book, LocalDate start,
                    LocalDate deadline, Boolean refunded, String checkoutNumber){
        this.customer = customer;
        this.book = book;
        this.start = start;
        this.deadline = deadline;
        this.refunded = refunded;
        this.checkoutNumber = checkoutNumber;
    }

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "checkout_book_fk_id"), nullable = false)
    private Book book;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "checkout_customer_fk_id"), nullable = false)
    private Customer customer;

    private boolean refunded;

    @Column(name = "checkout_number")
    private String checkoutNumber;

    // the date which the customer gets the book
    private LocalDate start;

    // the date which client must give back the book
    private LocalDate deadline;

    // the date that client actually give the book back
    private LocalDate end;
}
