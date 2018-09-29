package com.novatech.apitest.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novatech.apitest.api.CreateUserRequest;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateUserRequestTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializesToJSON() throws Exception {
        final CreateUserRequest userRequest = new CreateUserRequest("test_user", "password");

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/createUserRequest.json"), CreateUserRequest.class));

        assertThat(MAPPER.writeValueAsString(userRequest)).isEqualTo(expected);
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        final CreateUserRequest userRequest = new CreateUserRequest("test_user", "password");

        assertThat(MAPPER.readValue(fixture("fixtures/createUserRequest.json"), CreateUserRequest.class))
                .isEqualTo(userRequest);
    }
}
