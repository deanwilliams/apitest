package com.novatech.apitest.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateUserRequest {

    private int id;
    private String userName;
    @JsonProperty("password")
    private String clearTextPassword;

    public CreateUserRequest() {
        // No arg constructor
    }

    public CreateUserRequest(int id, String userName, String clearTextPassword) {
        this.id = id;
        this.userName = userName;
        this.clearTextPassword = clearTextPassword;
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("user_name")
    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return clearTextPassword;
    }

}
