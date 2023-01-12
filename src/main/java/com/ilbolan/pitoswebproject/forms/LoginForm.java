package com.ilbolan.pitoswebproject.forms;

import com.ilbolan.pitoswebproject.models.UserDAO;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

/**
 * The Form for logging in
 * It encapsulates data & performs check at field constrains
 * at runtime through annotations
 */
public class LoginForm {

    @NotNull(message = "Πρέπει να εισαχθεί όνομα χρήστη")
    private String username;

    @NotNull(message = "Πρέπει να εισαχθεί κωδικός χρήστη")
    private String password;

    @AssertTrue(message = "Ο λογαριασμός δεν υπάρχει")
    private boolean accountExists;

    public LoginForm(String username, String password){

        this.username = username;
        this.password = password;
        accountExists = (UserDAO.login(username, password) != null);
    }

    public LoginForm(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAccountExists() {
        return accountExists;
    }

    public void setAccountExists(boolean accountExists) {
        this.accountExists = accountExists;
    }
}
