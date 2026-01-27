package com.burakkurucay.connex.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private final boolean success;
    private final Instant timestamp;
    private final T data;
    private final ErrorInfo error;

    private ApiResponse(boolean success, T data, ErrorInfo error) {
        this.success = success;
        this.timestamp = Instant.now();
        this.data = data;
        this.error = error;
    }

    // Factory method for successful responses
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null);
    }

    // Factory method for error responses
    public static <T> ApiResponse<T> error(String code, String message, List<String> details) {
        ErrorInfo errorInfo = new ErrorInfo(code, message, details);
        return new ApiResponse<>(false, null, errorInfo);
    }

    // Overloaded error method without details
    public static <T> ApiResponse<T> error(String code, String message) {
        return error(code, message, null);
    }

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public T getData() {
        return data;
    }

    public ErrorInfo getError() {
        return error;
    }
}
