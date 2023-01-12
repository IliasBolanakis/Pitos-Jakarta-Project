package com.ilbolan.pitoswebproject.forms;

import jakarta.validation.constraints.*;

/**
 * The Form for resetting the password
 * It encapsulates data & performs check at field constrains
 * at runtime through annotations
 */
public class ResetPasswordForm {
    @Pattern(regexp = "^\\w{5,20}$", message = "Μη έγκυρο password (5-20 λατινικοί χαρακτήρες ή/και αριθμοί)")
    private String password;

    @Pattern(regexp = "^\\w{5,20}$", message = "Μη έγκυρο password (5-20 λατινικοί χαρακτήρες ή/και αριθμοί)")
    private String passwordConfirm;

    @AssertTrue(message = "Οι κωδικοί που δώσατε δεν είναι ίδιοι")
    private boolean passwordsMatch;

    public ResetPasswordForm(String password, String passwordConfirm) {
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        passwordsMatch = password.equals(passwordConfirm);
    }

    public ResetPasswordForm(){}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}