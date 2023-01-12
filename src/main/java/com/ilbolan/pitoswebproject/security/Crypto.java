package com.ilbolan.pitoswebproject.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * A class that offers encryption method for user's password
 */
public class Crypto {

    private Crypto(){} // no point in creating instances

    /**
     * Generates a hashed & salted version of a given password
     *
     * @param password the password to be hashed
     * @param salt the salt to be added to password
     *
     * @return a hashed & salted password
     */
    public static String hash(String password, String salt) {

        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            Base64.Decoder decoder = Base64.getDecoder();
            byte[] decodedSalt = decoder.decode(salt);
            md.update(decodedSalt);
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes)
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));

            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    /**
     * Method that generates a random salt
     *
     * @return A 16 byte secure random salt
     */
    public static String salt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        Base64.Encoder encoder = Base64.getEncoder();

        return encoder.encodeToString(salt);
    }
}