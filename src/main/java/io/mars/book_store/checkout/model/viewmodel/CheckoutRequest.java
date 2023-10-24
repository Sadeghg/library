package io.mars.book_store.checkout.model.viewmodel;

import lombok.Data;
@Data
public class CheckoutRequest {
    private String nationalCode;
    private String isbn;
    private boolean refunded;
    private String checkoutNumber;
}
