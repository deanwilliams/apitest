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
import java.util.Optional;

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
        Optional<User> foundUser = dao.getUserByUserName(userName);
        if (!foundUser.isPresent()) {
            throw new WebApplicationException("User " + userName + " does not exist", Response.Status.BAD_REQUEST);
        }
        return foundUser.get();
    }

}
