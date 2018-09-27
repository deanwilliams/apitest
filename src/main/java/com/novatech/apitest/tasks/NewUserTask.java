package com.novatech.apitest.tasks;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMultimap;
import com.novatech.apitest.auth.PasswordDigest;
import com.novatech.apitest.auth.PasswordManagementConfiguration;
import com.novatech.apitest.core.CreateUserRequest;
import com.novatech.apitest.core.User;
import com.novatech.apitest.db.UserDao;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.servlets.tasks.PostBodyTask;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

public class NewUserTask extends PostBodyTask {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    private final PasswordManagementConfiguration passwordManagement;
    private final UserDao dao;

    public NewUserTask(PasswordManagementConfiguration passwordManagement, UserDao dao) {
        super("new-user");
        this.passwordManagement = passwordManagement;
        this.dao = dao;
    }

    @Timed
    @Override
    public void execute(ImmutableMultimap<String, String> parameters, String postBody, PrintWriter output) throws Exception {
        CreateUserRequest createUserRequest = null;
        try {
            createUserRequest = MAPPER.readValue(postBody, CreateUserRequest.class);
        } catch (IOException e) {
            output.write("Invalid JSON");
            output.flush();
            return;
        }

        PasswordDigest digest = PasswordDigest.generateFromPassword(
                passwordManagement.getBcryptCost(), createUserRequest.getPassword());

        Optional<User> user = Optional.empty();
        try {
            int id = dao.createUser(createUserRequest.getUserName(), digest);
            user = dao.getUserById(id);
        } catch (UnableToExecuteStatementException e) {
            output.write("Duplicate Username. Choose another");
            output.flush();
            return;
        }
        if (user.isPresent()) {
            output.write("User " + user.get().getUserName() + " created");
        } else {
            output.write("User " + createUserRequest.getUserName() + " creation failed!");
        }
        output.flush();
    }

}
