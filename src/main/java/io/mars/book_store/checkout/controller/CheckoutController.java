package io.mars.book_store.checkout.controller;

import io.mars.book_store.checkout.model.entity.Checkout;
import io.mars.book_store.checkout.model.viewmodel.CheckoutRequest;
import io.mars.book_store.checkout.service.CheckoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/checkout/")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping(value = "add")
    private ResponseEntity<Checkout> save(@RequestBody CheckoutRequest checkoutRequest) {
        Checkout checkout = checkoutService.addCheckout(checkoutRequest);
        return ResponseEntity.ok(checkout);
    }

    @PostMapping(value = "refund")
    private ResponseEntity<Checkout> refund(@RequestBody CheckoutRequest checkoutRequest) {
        Checkout checkout = checkoutRequest.getCheckoutNumber() != null
                ? checkoutService.refund(checkoutRequest.getCheckoutNumber())
                : checkoutService.refund(checkoutRequest);
        return ResponseEntity.ok(checkout);
    }

    @GetMapping(value = "report")
    private void report() {
        checkoutService.report();
    }
}
