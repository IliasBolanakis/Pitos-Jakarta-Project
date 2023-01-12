package com.ilbolan.pitoswebproject.forms.annotations.validators;

import com.ilbolan.pitoswebproject.forms.annotations.OrderTimeConstrains;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Validator for OrderTimeConstrains annotation
 */
public class OrderTimeValidator implements ConstraintValidator<OrderTimeConstrains, LocalDateTime> {
    @Override
    public void initialize(OrderTimeConstrains constraintAnnotation) {}

    @Override
    public boolean isValid(LocalDateTime orderTime, ConstraintValidatorContext constraintValidatorContext) {
        // set ordering time-window 12:00 - 22:00
        return orderTime.isAfter(
                        LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0,0,0)))
                && orderTime.isBefore(
                        LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59,0,0)));
    }
}
