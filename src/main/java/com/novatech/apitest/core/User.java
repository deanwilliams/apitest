package com.novatech.apitest.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.novatech.apitest.auth.PasswordDigest;

import java.security.Principal;

public class User implements Principal {

    private int id;
    private String userName;
    private PasswordDigest passwordDigest;

    public User() {
        // No arg constructor
    }

    public User(int id, String userName, PasswordDigest passwordDigest) {
        this.id = id;
        this.userName = userName;
        this.passwordDigest = passwordDigest;
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("user_name")
    public String getUserName() {
        return userName;
    }

    @JsonProperty("password_digest")
    public PasswordDigest getPasswordDigest() {
        return passwordDigest;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", passwordDigest='" + passwordDigest.getDigest() + '\'' +
                '}';
    }

    @Override
    public String getName() {
        return userName;
    }
}
