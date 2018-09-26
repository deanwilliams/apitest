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
        Optional<User> user = dao.getUserByUserName(basicCredentials.getUsername());

        boolean validUser = false;
        if (user.isPresent()) {
            PasswordDigest digest = user.get().getPasswordDigest();
            validUser = digest.checkPassword(basicCredentials.getPassword());
        }
        return validUser ? user : Optional.empty();
    }

}
