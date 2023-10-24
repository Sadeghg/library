package io.mars.book_store.checkout.service.validation;

import io.mars.book_store.checkout.model.entity.Checkout;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.util.function.Predicate;

public class CheckoutValidator implements ConstraintValidator<CheckoutValidDate, Checkout> {
    @Override
    public void initialize(CheckoutValidDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    @Override
    public boolean isValid(Checkout value, ConstraintValidatorContext context) {

        LocalDate deadline = value.getDeadline();
        LocalDate start = value.getStart();
        LocalDate end = value.getEnd();

        if (deadline == null || start == null){
            context.buildConstraintViolationWithTemplate("start and deadline dates cannot be empty");
            return false;
        }

        Predicate<LocalDate> deadlineCheck = s -> !((s.isBefore(deadline)) || s.isEqual(deadline));
        Predicate<LocalDate> endCheck = s -> end != null && !((s.isBefore(end)) || s.isEqual(end));

        if (deadlineCheck.or(endCheck).test(start)){
            context.buildConstraintViolationWithTemplate("start must be before the end and deadline");
            return false;
        }

        return true;
    }
}
