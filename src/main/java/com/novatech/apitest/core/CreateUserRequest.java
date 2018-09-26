package com.novatech.apitest.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateUserRequest {

    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("password")
    private String clearTextPassword;

    public CreateUserRequest() {
        // No arg constructor
    }

    public CreateUserRequest(String userName, String clearTextPassword) {
        this.userName = userName;
        this.clearTextPassword = clearTextPassword;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return clearTextPassword;
    }

}
