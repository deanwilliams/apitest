package com.novatech.apitest.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novatech.apitest.auth.PasswordDigest;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;
import sun.security.util.Password;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    private static final PasswordDigest DIGEST = PasswordDigest.fromDigest("$2a$10$Jaff5pirlbAj.XET73kZvuPl3ill5v8Z3ZFg8AF2Oyopqmg9VOque");

    @Test
    public void serializesToJSON() throws Exception {
        final User user = new User(1, "test_user", DIGEST);

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/user.json"), User.class));

        assertThat(MAPPER.writeValueAsString(user)).isEqualTo(expected);
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        final User user = new User(1, "test_user", DIGEST);

        assertThat(MAPPER.readValue(fixture("fixtures/user.json"), User.class))
                .isEqualTo(user);
    }
}
