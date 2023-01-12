package com.ilbolan.pitoswebproject.forms.annotations;

import com.ilbolan.pitoswebproject.forms.annotations.validators.EmailAvailableValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Annotation that checks if the
 * email is available
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailAvailableValidator.class)
public @interface EmailAvailableConstrains {

    String message() default "Το email χρησιμοποιείται ήδη";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
