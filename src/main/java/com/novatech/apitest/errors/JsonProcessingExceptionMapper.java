package com.novatech.apitest.errors;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class JsonProcessingExceptionMapper implements ExceptionMapper<JsonProcessingException> {

    @Override
    public Response toResponse(JsonProcessingException e) {
        if (e instanceof JsonGenerationException) {
            return Response.serverError()
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .entity(ErrorMessageFormatter.formatErrorMessage(
                            Response.Status.INTERNAL_SERVER_ERROR, "Error generating JSON"))
                    .build();
        }

        final String message = e.getOriginalMessage();
        if (message.startsWith("No suitable constructor found")) {
            return Response.serverError()
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .entity(ErrorMessageFormatter.formatErrorMessage(
                            Response.Status.INTERNAL_SERVER_ERROR, "Unable to deserialize the specific type"))
                    .build();
        }

        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(ErrorMessageFormatter.formatErrorMessage(
                        Response.Status.BAD_REQUEST, "Unable to process JSON"))
                .build();
    }

}
