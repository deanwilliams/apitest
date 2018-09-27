package com.novatech.apitest.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Objects;

public class PasswordDigest {

    private final String digest;

    @JsonCreator
    private PasswordDigest(@JsonProperty("password_digest") final String digest) {
        this.digest = digest;
    }

    @JsonProperty("password_digest")
    public String getDigest() {
        return digest;
    }

    public boolean checkPassword(final String passwordToCheck) {
        return BCrypt.checkpw(passwordToCheck, digest);
    }

    public static PasswordDigest fromDigest(final String digest) {
        return new PasswordDigest(digest);
    }

    public static PasswordDigest generateFromPassword(final int bcryptCost, final String password) {
        final String salt = BCrypt.gensalt(bcryptCost);
        return PasswordDigest.fromDigest(BCrypt.hashpw(password, salt));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasswordDigest that = (PasswordDigest) o;
        return Objects.equals(digest, that.digest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(digest);
    }
}
