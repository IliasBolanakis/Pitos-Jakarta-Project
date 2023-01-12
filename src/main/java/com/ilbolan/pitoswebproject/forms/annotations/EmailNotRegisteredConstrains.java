package com.ilbolan.pitoswebproject.forms.annotations;

import com.ilbolan.pitoswebproject.forms.annotations.validators.EmailNotRegisteredValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that checks if the email
 * is already registered in the database
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailNotRegisteredValidator.class)
public @interface EmailNotRegisteredConstrains {

    String message() default "Το δεν email είναι καταχωρημένο";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
