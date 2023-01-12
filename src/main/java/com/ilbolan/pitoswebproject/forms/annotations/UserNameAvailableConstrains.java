package com.ilbolan.pitoswebproject.forms.annotations;

import com.ilbolan.pitoswebproject.forms.annotations.validators.UserNameAvailableValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that checks if the
 * username is available
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserNameAvailableValidator.class)
public @interface UserNameAvailableConstrains {

    String message() default "Το όνομα χρήστη χρησιμοποιείται ήδη";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
