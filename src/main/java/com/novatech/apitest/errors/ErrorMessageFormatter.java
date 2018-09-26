package com.novatech.apitest.errors;

import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ErrorMessageFormatter {

    private ErrorMessageFormatter() {
        // No instantiation
    }

    public static HashMap<String, Object> formatErrorMessage(Response.Status status, String message) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String timestamp = sdf.format(Calendar.getInstance().getTime());

        HashMap<String, Object> entity = new HashMap<>();
        entity.put("timestamp", timestamp);
        entity.put("code", status.getStatusCode());
        entity.put("status", status.getReasonPhrase());
        entity.put("error", message);
        return entity;
    }
}
