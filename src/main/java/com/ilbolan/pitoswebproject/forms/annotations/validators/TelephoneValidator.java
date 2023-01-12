package com.ilbolan.pitoswebproject.forms.annotations.validators;

import com.ilbolan.pitoswebproject.forms.annotations.TelephoneConstrains;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * Validator for TelephoneConstrains annotation
 */
public class TelephoneValidator implements ConstraintValidator<TelephoneConstrains, String> {


    @Override
    public void initialize(TelephoneConstrains constraintAnnotation) {}

    @Override
    public boolean isValid(String tel, ConstraintValidatorContext constraintValidatorContext) {
        // regex for greek telephone number
        return Pattern.matches("^[26]\\d{9}$|^$", tel);
    }
}
