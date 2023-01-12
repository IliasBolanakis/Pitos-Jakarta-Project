package com.ilbolan.pitoswebproject.forms;

import com.ilbolan.pitoswebproject.forms.annotations.EmailConstrains;
import com.ilbolan.pitoswebproject.forms.annotations.EmailNotRegisteredConstrains;
import jakarta.validation.constraints.*;

/**
 * The Form for entering the email for password reset
 * It encapsulates data & performs check at field constrains
 * at runtime through annotations
 */
public class ResetEmailForm {
    @NotNull (message = "Πρέπει να εισαχθεί email")
    @NotEmpty (message = "To e-mail πρέπει να μην είναι κενό")
    @EmailConstrains
    @EmailNotRegisteredConstrains
    private String email;

    public ResetEmailForm(String email) {
        this.email = email;
    }

    public ResetEmailForm(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}