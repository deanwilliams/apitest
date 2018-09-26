package com.novatech.apitest.resources;

import com.codahale.metrics.annotation.Timed;
import com.novatech.apitest.auth.PasswordDigest;
import com.novatech.apitest.auth.PasswordManagementConfiguration;
import com.novatech.apitest.core.CreateUserRequest;
import com.novatech.apitest.core.User;
import com.novatech.apitest.db.UserDao;
import io.dropwizard.auth.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    private PasswordManagementConfiguration passwordManagement;
    private UserDao dao;


    public UserResource(PasswordManagementConfiguration passwordManagement, UserDao dao) {
        this.passwordManagement = passwordManagement;
        this.dao = dao;
    }

    @PermitAll
    @GET
    @Timed
    public List<User> getUsers(@Auth User user) {
        LOGGER.info("Getting all users");
        return dao.getUsers();
    }

    @PermitAll
    @GET
    @Timed
    @Path("/{id}")
    public User getUserById(@Auth User user, @PathParam("id") long id) {
        LOGGER.info("Getting user by ID: " + id);
        User foundUser = dao.getUserById(id);
        if (foundUser == null) {
            throw new WebApplicationException("User ID " + id + " is not valid", Response.Status.BAD_REQUEST);
        }
        return foundUser;
    }

    @POST
    @Timed
    @Path("/new")
    public User createUser(CreateUserRequest createUserRequest) {
        if (createUserRequest == null) {
            throw new WebApplicationException("Unable to process JSON", Response.Status.BAD_REQUEST);
        }
        LOGGER.info("Creating new user...");
        User newUser = new User(
                createUserRequest.getId(),
                createUserRequest.getUserName(),
                PasswordDigest.generateFromPassword(
                        passwordManagement.getBcryptCost(), createUserRequest.getPassword()));

        boolean success = dao.createUser(newUser);
        if (success) {
            return dao.getUserById(newUser.getId());
        } else {
            throw new WebApplicationException("Unable to create user", Response.Status.BAD_REQUEST);
        }
    }
}
