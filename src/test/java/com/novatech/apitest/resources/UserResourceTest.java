package com.novatech.apitest.resources;

import com.novatech.apitest.auth.PasswordDigest;
import com.novatech.apitest.auth.PasswordManagementConfiguration;
import com.novatech.apitest.core.User;
import com.novatech.apitest.db.UserDao;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.GenericType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UserResourceTest {

    private static final PasswordManagementConfiguration passwordConfiguration = new PasswordManagementConfiguration();
    private static final UserDao dao = mock(UserDao.class);

    private static final PasswordDigest DIGEST = PasswordDigest.fromDigest("$2a$10$Jaff5pirlbAj.XET73kZvuPl3ill5v8Z3ZFg8AF2Oyopqmg9VOque");
    private static final User USER = new User(1, "test_user", DIGEST);
    private static final List<User> USERS = new ArrayList<>();

    {
        passwordConfiguration.setBcryptCost(10);
        USERS.add(USER);
    }

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new UserResource(passwordConfiguration, dao))
            .build();

    @Before
    public void setup() {
        when(dao.getUserByUserName(eq("test_user"))).thenReturn(Optional.of(USER));
        when(dao.getUsers()).thenReturn(USERS);
    }

    @After
    public void tearDown() {
        // we have to reset the mock after each test because of the @ClassRule
        reset(dao);
    }

    @Test
    public void testGetUsers() {
        assertThat(resources.target("/users").request().get(new GenericType<List<User>>() {}))
                .isEqualTo(USERS);
    }

    @Test
    public void testGetUserByUserName() {
        assertThat(resources.target("/users/test_user").request().get(User.class)).isEqualTo(USER);
    }
}
