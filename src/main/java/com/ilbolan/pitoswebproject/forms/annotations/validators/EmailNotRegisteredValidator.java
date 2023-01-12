package com.ilbolan.pitoswebproject.forms.annotations.validators;

import com.ilbolan.pitoswebproject.forms.annotations.EmailNotRegisteredConstrains;
import com.ilbolan.pitoswebproject.models.UserDAO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.io.Serializable;

/**
 * Validator class for EmailNotRegisteredConstrains annotation
 */
public class EmailNotRegisteredValidator implements ConstraintValidator<EmailNotRegisteredConstrains, String> {
    @Override
    public void initialize(EmailNotRegisteredConstrains constraintAnnotation) {}

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return UserDAO.getUserByEmail(email) != null;
    }
}
