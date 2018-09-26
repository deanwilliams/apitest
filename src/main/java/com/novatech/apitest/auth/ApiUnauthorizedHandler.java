package com.novatech.apitest.auth;

import com.novatech.apitest.errors.ErrorMessageFormatter;
import io.dropwizard.auth.UnauthorizedHandler;

import javax.ws.rs.core.Response;

public class ApiUnauthorizedHandler implements UnauthorizedHandler {

    @Override
    public Response buildResponse(String prefix, String realm) {
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(ErrorMessageFormatter.formatErrorMessage(
                        Response.Status.fromStatusCode(401), "Credentials are required to access this resource."))
                .build();
    }

}
