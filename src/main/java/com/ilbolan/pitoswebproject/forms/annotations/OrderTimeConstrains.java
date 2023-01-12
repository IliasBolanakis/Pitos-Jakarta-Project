package com.ilbolan.pitoswebproject.forms.annotations;

import com.ilbolan.pitoswebproject.forms.annotations.validators.OrderTimeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Order time constrains annotation
 * Valid hours: 18:00-22:00 (greek time)
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OrderTimeValidator.class)
public @interface OrderTimeConstrains {
    String message() default "Εκτός ωραρίου παραγγελιών";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
