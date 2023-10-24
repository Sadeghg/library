package io.mars.book_store.common.exception;

import lombok.Data;

import java.util.List;

@Data
public class ConstraintsViolationException extends RuntimeException{
    private final List<String> violations;

    public ConstraintsViolationException(List<String> violations) {
        this.violations = violations;
    }
}
