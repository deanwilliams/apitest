package com.novatech.apitest.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.jackson.JsonSnakeCase;

import java.util.Objects;

@JsonSnakeCase
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateUserRequest that = (CreateUserRequest) o;
        return Objects.equals(userName, that.userName) &&
                Objects.equals(clearTextPassword, that.clearTextPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, clearTextPassword);
    }
}
