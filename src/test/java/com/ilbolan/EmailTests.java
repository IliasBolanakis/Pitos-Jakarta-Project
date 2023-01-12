package com.ilbolan;

import com.ilbolan.pitoswebproject.forms.annotations.validators.EmailValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class EmailTests {

    static EmailValidator validator;

    @BeforeAll
    public static void initValidator(){
        validator = new EmailValidator();
    }


    @DisplayName("Valid Emails")
    @ParameterizedTest
    @ValueSource(strings = {"hlias.bolanakes@gmail.com","elias.bolanakes@gmail.com"})
    public void testValidEmails(String email) {
        Assertions.assertTrue(validator.isAddressValid(email));
    }

    @DisplayName("Invalid Emails")
    @ParameterizedTest
    @ValueSource(strings = {"hlias.bolanakes123@gmail.com", "ibolanakis@hotmail.com"})
    public void testInvalidEmails(String email) {
        try {
            Assertions.assertFalse(validator.isAddressValid(email));
        } catch (Exception e){} // throws exception in some invalid occasions
    }

    @DisplayName("Invalid Domains")
    @ParameterizedTest
    @ValueSource(strings = {"hlias.bolanakes@nowhere.com", "ibolanakis@gmail.abcd"})
    public void testInvalidDomains(String email) {
        try {
            Assertions.assertFalse(validator.isAddressValid(email));
        } catch (Exception e){} // throws exception in some invalid occasions
    }
}
