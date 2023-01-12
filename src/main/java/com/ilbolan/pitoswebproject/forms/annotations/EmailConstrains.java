package com.ilbolan.pitoswebproject.forms.annotations;

import com.ilbolan.pitoswebproject.forms.annotations.validators.EmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Email constrains annotation that check for correct email structure,
 * valid domain name & its actual existence
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
public @interface EmailConstrains {
    String message() default "Μη έγκυρο email";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
