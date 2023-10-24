package io.mars.book_store.checkout.service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckoutValidator.class)
public @interface CheckoutValidDate {
    String message() default "start date must be before end and deadline";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
