package io.mars.book_store.base;

import io.mars.book_store.common.exception.ConstraintsViolationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.Set;


/**
    responsible for validating the base object of each entity
 **/
public abstract class BaseService<T extends BaseEntity> {

    private final Validator validator;

    protected BaseService(Validator validator) {
        this.validator = validator;
    }

    public void validate(T t) {
        Set<ConstraintViolation<T>> violations = validator.validate(t);
        if (violations.size() == 0) return;
        throw new ConstraintsViolationException(violations.stream().map(ConstraintViolation::getMessage).toList());
    }
}
