package com.burakkurucay.connex.dto.common;

import java.util.List;

public class ErrorInfo {

    private final String code;
    private final String message;
    private final List<String> details;

    public ErrorInfo(String code, String message, List<String> details) {
        this.code = code;
        this.message = message;
        this.details = details;
    }

    // Getters
    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getDetails() {
        return details;
    }
}
