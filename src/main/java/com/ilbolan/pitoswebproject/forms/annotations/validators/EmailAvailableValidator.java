package com.ilbolan.pitoswebproject.forms.annotations.validators;

import com.ilbolan.pitoswebproject.forms.annotations.EmailAvailableConstrains;
import com.ilbolan.pitoswebproject.models.UserDAO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.io.Serializable;

/**
 * Validator class for EmailAvailableConstrains annotation
 */
public class EmailAvailableValidator implements ConstraintValidator<EmailAvailableConstrains, String> {
    @Override
    public void initialize(EmailAvailableConstrains constraintAnnotation) {}

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return UserDAO.getUserByEmail(email) == null; // if returned object is null no user with such email exists
    }
}
