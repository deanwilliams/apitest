package com.novatech.apitest.tasks;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMultimap;
import com.novatech.apitest.auth.PasswordDigest;
import com.novatech.apitest.auth.PasswordManagementConfiguration;
import com.novatech.apitest.core.CreateUserRequest;
import com.novatech.apitest.core.User;
import com.novatech.apitest.db.UserDao;
import io.dropwizard.servlets.tasks.PostBodyTask;
import io.dropwizard.servlets.tasks.Task;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;

import java.io.IOException;
import java.io.PrintWriter;

public class NewUserTask extends PostBodyTask {

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
        ObjectMapper mapper = new ObjectMapper();
        CreateUserRequest createUserRequest = null;
        try {
            createUserRequest = mapper.readValue(postBody, CreateUserRequest.class);
        } catch (IOException e) {
            output.write("Invalid JSON");
            output.flush();
            return;
        }

        PasswordDigest digest = PasswordDigest.generateFromPassword(
                passwordManagement.getBcryptCost(), createUserRequest.getPassword());

        User user = null;
        try {
            int id = dao.createUser(createUserRequest.getUserName(), digest);
            user = dao.getUserById(id);
        } catch (UnableToExecuteStatementException e) {
            output.write("Duplicate Username. Choose another");
            output.flush();
            return;
        }
        if (user != null) {
            output.write("User " + user.getUserName() + " created");
        } else {
            output.write("User " + user.getUserName() + " creation failed!");
        }
        output.flush();
    }

}
