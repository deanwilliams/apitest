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
    @Path("/{user_name}")
    public User getUserByUsername(@Auth User user, @PathParam("user_name") String userName) {
        LOGGER.info("Getting user: " + userName);
        User foundUser = dao.getUserByUserName(userName);
        if (foundUser == null) {
            throw new WebApplicationException("User " + userName + " does not exist", Response.Status.BAD_REQUEST);
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

        int id = dao.createUser(createUserRequest.getUserName(), PasswordDigest.generateFromPassword(
                passwordManagement.getBcryptCost(), createUserRequest.getPassword()));
        User user = dao.getUserById(id);
        if (user == null) {
            throw new WebApplicationException("Unable to create user", Response.Status.BAD_REQUEST);
        }
        return user;
    }
}
