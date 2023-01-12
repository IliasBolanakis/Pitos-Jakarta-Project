package com.ilbolan.pitoswebproject.forms.annotations;

import com.ilbolan.pitoswebproject.forms.annotations.validators.OrderAmountValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Order constrains annotation
 * 1) At least 1 pie total
 * 2) 0-100 for each pie
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OrderAmountValidator.class)
public @interface OrderAmountConstrains {
    String message() default "Μη έγκυρες ποσότητες αντικειμένων (0-100 για κάθε πίτα και 1 τουλάχιστον συνολικά)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
