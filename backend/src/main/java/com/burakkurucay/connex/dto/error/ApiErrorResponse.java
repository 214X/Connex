package com.burakkurucay.connex.dto.error;

import java.time.Instant;
import java.util.List;

public class ApiErrorResponse {

    private final int status;
    private final String error;
    private final String message;
    private final Instant timestamp;
    private final String path;
    private final List<String> details;

    public ApiErrorResponse(
        int status,
        String error,
        String message,
        Instant timestamp,
        String path,
        List<String> details
    ) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
        this.path = path;
        this.details = details;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }

    public List<String> getDetails() {
        return details;
    }
}

