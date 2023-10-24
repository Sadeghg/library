package io.mars.book_store.common.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorMessage {

    private Date timestamp;
    private List<String> messages;
    private String description;
}