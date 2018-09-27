package com.novatech.apitest.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.novatech.apitest.auth.PasswordDigest;
import io.dropwizard.jackson.JsonSnakeCase;

import java.security.Principal;
import java.util.Objects;

@JsonSnakeCase
public class User implements Principal {

    private final int id;
    private final String userName;
    private final PasswordDigest passwordDigest;

    @JsonCreator
    public User(@JsonProperty("id") int id, @JsonProperty("user_name") String userName,
                @JsonProperty("password_digest") PasswordDigest passwordDigest) {
        this.id = id;
        this.userName = userName;
        this.passwordDigest = passwordDigest;
    }

    @JsonProperty
    public int getId() {
        return id;
    }

    @JsonProperty
    public String getUserName() {
        return userName;
    }

    @JsonProperty
    public PasswordDigest getPasswordDigest() {
        return passwordDigest;
    }

    @JsonIgnore
    @Override
    public String getName() {
        return userName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("id=").append(id);
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", passwordDigest=").append(passwordDigest);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(passwordDigest, user.passwordDigest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, passwordDigest);
    }
}
