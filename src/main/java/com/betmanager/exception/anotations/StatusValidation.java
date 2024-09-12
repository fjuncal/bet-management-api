package com.betmanager.exception.anotations;

import com.betmanager.exception.anotations.impl.StatusValidationImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = StatusValidationImpl.class)
public @interface StatusValidation {
    String message() default "Status must be PENDING, WON, LOST or CANCELLED";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
