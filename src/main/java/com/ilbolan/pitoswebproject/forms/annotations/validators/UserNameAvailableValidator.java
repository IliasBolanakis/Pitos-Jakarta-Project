package com.ilbolan.pitoswebproject.forms.annotations.validators;

import com.ilbolan.pitoswebproject.forms.annotations.UserNameAvailableConstrains;
import com.ilbolan.pitoswebproject.models.UserDAO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.io.Serializable;

/**
 * Validator class for UserNameAvailableConstrains annotation
 */
public class UserNameAvailableValidator implements ConstraintValidator<UserNameAvailableConstrains, String> {
    @Override
    public void initialize(UserNameAvailableConstrains constraintAnnotation) {}

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return UserDAO.getUserByUsername(username) == null;
    }
}
