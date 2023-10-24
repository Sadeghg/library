package io.mars.book_store.checkout.report;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckoutCSVDTO {

    private String fullName;
    private String bookInfo;
    private String duration;
    private String start;
    private String deadline;
}
