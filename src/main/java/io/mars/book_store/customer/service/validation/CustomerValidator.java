package io.mars.book_store.customer.service.validation;

import io.mars.book_store.customer.model.entity.Customer;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class CustomerValidator implements ConstraintValidator<ValidCustomer, Customer> {
    @Override
    public void initialize(ValidCustomer constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Customer value, ConstraintValidatorContext context) {

        LocalDate birthDate = value.getBirthDate();
        LocalDate membershipDate = value.getMembershipDate();

        if (birthDate == null || membershipDate == null){
            context.buildConstraintViolationWithTemplate("birth and membership dates cannot be empty");
            return false;
        }

        if (!birthDate.isBefore(membershipDate)){
            context.buildConstraintViolationWithTemplate("birth must be before the membership");
            return false;
        }

        if (value.getNationalCode().length() != 10){
            context.buildConstraintViolationWithTemplate("nationalCode must be exactly 10 number");
            return false;
        }

        if (value.getCheckoutCount() > 2 || value.getCheckoutCount() < 0){
            context.buildConstraintViolationWithTemplate("the maximum checkouts of a customer is 2");
            return false;
        }

        return true;
    }
}
