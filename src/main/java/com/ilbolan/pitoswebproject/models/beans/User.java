package com.ilbolan.pitoswebproject.models.beans;

import java.io.Serializable;

/**
 * A user bean needed for reading/writing to the Database
 */
public class User implements Serializable {

    private int id;

    private String username;

    private String password;

    private String fullName;

    private String address;

    private String email;

    private String tel;

    private String status;

    private String code;

    public User() {}

    public User(String username, String password, String fullName, String email, String tel, String status, String code) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.tel = tel;
        this.status = status;
        this.code = code;
    }

    public User(String username, String password, String fullName, String email, String tel) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.tel = tel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}