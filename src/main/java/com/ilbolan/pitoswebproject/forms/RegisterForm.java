package com.ilbolan.pitoswebproject.forms;

import com.ilbolan.pitoswebproject.forms.annotations.EmailAvailableConstrains;
import com.ilbolan.pitoswebproject.forms.annotations.EmailConstrains;
import com.ilbolan.pitoswebproject.forms.annotations.TelephoneConstrains;
import com.ilbolan.pitoswebproject.forms.annotations.UserNameAvailableConstrains;
import com.ilbolan.pitoswebproject.models.beans.User;
import jakarta.validation.constraints.*;

/**
 * The Form for registering to the website
 * It encapsulates data & performs check at field constrains
 * at runtime through annotations
 */
public class RegisterForm {
    @NotNull (message = "Πρέπει να εισαχθεί το ονοματεπώνυμο")
    @NotEmpty (message = "Το ονοματεπώνυμο πρέπει να μην είναι κενό")
    private String fullName;

    @NotNull (message = "Πρέπει να εισαχθεί το email")
    @EmailConstrains
    @EmailAvailableConstrains
    private String email;

    @NotNull (message = "Πρέπει να εισαχθεί το διεύθυνση")
    @NotEmpty (message = "Η διεύθυνση πρέπει να μην είναι κενή")
    private String address;

    @NotNull (message = "Πρέπει να εισαχθεί το τηλέφωνο")
    @TelephoneConstrains
    private String tel;

    @NotNull (message = "Πρέπει να εισαχθεί το όνομα χρήστη")
    @Pattern(regexp = "^\\w{5,20}$", message = "Μη έγκυρο username (5-20 λατινικοί χαρακτήρες ή/και αριθμοί)")
    @UserNameAvailableConstrains
    private String username;

    @NotNull (message = "Πρέπει να εισαχθεί ο κωδικός")
    @Pattern(regexp = "^\\w{5,20}$", message = "Μη έγκυρο password (5-20 λατινικοί χαρακτήρες ή/και αριθμοί)")
    private String password;

    @NotNull (message = "Πρέπει να εισαχθεί η επιβεβαίωση κωδικού")
    @Pattern(regexp = "^\\w{5,20}$", message = "Μη έγκυρο password (5-20 λατινικοί χαρακτήρες ή/και αριθμοί)")
    private String passwordConfirm;

    @NotNull
    @AssertTrue(message = "Οι κωδικοί που δώσατε δεν είναι ίδιοι")
    private Boolean passwordsMatch;

    public RegisterForm(String fullName, String email, String tel, String address,
                        String username, String password, String passwordConfirm) {
        this.fullName = fullName;
        this.email = email;
        this.tel = tel;
        this.address = address;
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.passwordsMatch = password.equals(passwordConfirm);
    }

    public RegisterForm() {}

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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

    public String getPassword2() {
        return passwordConfirm;
    }

    public void setPassword2(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Boolean getPasswordsMatch() {
        return passwordsMatch;
    }

    public void setPasswordsMatch(Boolean passwordsMatch) {
        this.passwordsMatch = passwordsMatch;
    }
}