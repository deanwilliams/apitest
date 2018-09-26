package com.novatech.apitest.auth;

import com.novatech.apitest.core.User;
import com.novatech.apitest.db.UserDao;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ApiAuthenticator implements Authenticator<BasicCredentials, User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiAuthenticator.class);

    private UserDao dao;

    public ApiAuthenticator(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public Optional<User> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
        User user = dao.getUserByUserName(basicCredentials.getUsername());
        if (user == null) {
            return Optional.empty();
        }

        PasswordDigest digest = user.getPasswordDigest();
        boolean validPassword = digest.checkPassword(basicCredentials.getPassword());
        return validPassword ? Optional.of(user) : Optional.empty();
    }

}
