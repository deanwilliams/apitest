package com.novatech.apitest.errors;

import org.eclipse.jetty.http.HttpStatus;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class WebExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(WebApplicationException e) {
        // If the message did not come with a status, we will default to an internal
        // server error status.
        int status = e.getResponse() == null ? 500: e.getResponse().getStatus();

        // Get a nice human readable message for our status code if the exception
        // doesn't already have a message
        final String msg = e.getMessage() == null ? HttpStatus.getMessage(status) : e.getMessage();

        // Create a JSON response with the provided hashmap
        return Response.status(status)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(ErrorMessageFormatter.formatErrorMessage(Response.Status.fromStatusCode(status), msg))
                .build();
    }

}
