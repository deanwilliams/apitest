package com.novatech.apitest.errors;

import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class UnableToExecuteStatementExceptionMapper implements ExceptionMapper<UnableToExecuteStatementException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnableToExecuteStatementExceptionMapper.class);

    @Override
    public Response toResponse(UnableToExecuteStatementException e) {
        LOGGER.error(e.getMessage(), e);

        // Username already exists in DB
        if (e.getMessage().contains("duplicate key value")) {
            return Response.status(Response.Status.CONFLICT)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .entity(ErrorMessageFormatter.formatErrorMessage(Response.Status.CONFLICT, "Username already exists"))
                    .build();
        }

        // Default to an internal server error status.
        int status = 500;

        // Unexpected database error
        final String msg = "Unexpected error communicating with Database";

        // Create a JSON response with the provided hashmap
        return Response.status(status)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(ErrorMessageFormatter.formatErrorMessage(Response.Status.fromStatusCode(status), msg))
                .build();
    }

}
