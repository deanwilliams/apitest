package com.novatech.apitest.errors;

import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class UnableToRunSQLExceptionMapper implements ExceptionMapper<UnableToExecuteStatementException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnableToRunSQLExceptionMapper.class);

    @Override
    public Response toResponse(UnableToExecuteStatementException e) {
        LOGGER.error(e.getMessage(), e);

        // Default to an internal server error status.
        int status = 500;

        // Get a nice human readable message for our status code if the exception
        // doesn't already have a message
        final String msg = "Unexpected error communicating with Database";

        // Create a JSON response with the provided hashmap
        return Response.status(status)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(ErrorMessageFormatter.formatErrorMessage(Response.Status.fromStatusCode(status), msg))
                .build();
    }

}
