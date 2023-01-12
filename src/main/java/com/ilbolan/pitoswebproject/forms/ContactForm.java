package com.ilbolan.pitoswebproject.forms;

import jakarta.validation.constraints.*;

/**
 * The Form for contacting the store
 * It encapsulates data & performs check at field constrains
 * at runtime through annotations
 */
public class ContactForm {
    @NotNull (message = "Το ονοματεπώνυμο πρέπει να μην είναι null")
    @NotEmpty (message = "Το ονοματεπώνυμο πρέπει να μην είναι κενό")
    private String fullName;

    @NotNull (message = "Το e-mail πρέπει να μην είναι null")
    @NotEmpty (message = "To e-mail πρέπει να μην είναι κενό")
    @Email (message = "Το e-mail δεν είναι έγκυρο")
    private String email;

    @Pattern(regexp = "^[26][0-9]{9}$|^$", message = "Μη έγκυρο τηλέφωνο")
    private String tel;

    @Pattern(regexp = ".{5,}", message = "Το μήνυμα πρέπει να έχει τουλάχιστον 5 χαρακτήρες")
    @Pattern(regexp = ".{0,100}", message = "Το μήνυμα πρέπει να έχει το πολύ 100 χαρακτήρες")
    private String message;

    public ContactForm(String fullName, String email, String tel, String message) {
        this.fullName = fullName;
        this.email = email;
        this.tel = tel;
        this.message = message;
    }

    public ContactForm(){}

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}