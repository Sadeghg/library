package io.mars.book_store.checkout.service;

import io.mars.book_store.checkout.model.entity.Checkout;
import io.mars.book_store.checkout.model.viewmodel.CheckoutRequest;

public interface CheckoutService {

    Checkout addCheckout(CheckoutRequest checkoutRequest);

    Checkout refund(CheckoutRequest checkoutRequest);

    Checkout refund(String checkoutNumber);

    void scoreCheckouts();

    void report();

//    void reportScheduled();
}
