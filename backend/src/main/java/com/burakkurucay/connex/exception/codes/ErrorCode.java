package com.burakkurucay.connex.exception.codes;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // common errors
    VALIDATION_ERROR(1000, "Validation error", HttpStatus.BAD_REQUEST),
    BUSINESS_ERROR(1001, "Business error", HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR(1002, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),

    // register errors
    PASSWORD_MISSMATCH(2000, "Password mismatch", HttpStatus.BAD_REQUEST),
    USER_ALREADY_EXISTS(2001, "User already exists", HttpStatus.CONFLICT),

    // user errors
    USER_NOT_FOUND(3000, "User not found", HttpStatus.NOT_FOUND),

    // --- Security ---
    AUTH_UNAUTHORIZED(4000, "Unauthorized access", HttpStatus.UNAUTHORIZED), // no / invalid Token
    AUTH_FORBIDDEN(4001, "Access forbidden", HttpStatus.FORBIDDEN), // no authorization
    AUTH_TOKEN_EXPIRED(4002, "Authentication token expired", HttpStatus.UNAUTHORIZED), // auth token expired
    AUTH_INVALID_CREDENTIALS(4003, "Invalid credentials", HttpStatus.UNAUTHORIZED),
    AUTH_ACCOUNT_LOCKED(4004, "Account locked", HttpStatus.FORBIDDEN), // account suspended
    AUTH_ACCOUNT_DISABLED(4005, "Account disabled", HttpStatus.FORBIDDEN), // account inactive

    // profile
    PROFILE_ALREADY_EXISTS(5000, "Profile already exists", HttpStatus.CONFLICT),
    PROFILE_NOT_FOUND(5001, "Profile not found", HttpStatus.NOT_FOUND),

    // pp contact
    PROFILE_CONTACT_NOT_FOUND(6000, "Contact entry not found", HttpStatus.NOT_FOUND),

    // pp education
    PROFILE_EDUCATION_NOT_FOUND(7000, "Education entry not found", HttpStatus.NOT_FOUND),

    // pp experience
    PROFILE_EXPERIENCE_NOT_FOUND(8000, "Experience entry not found", HttpStatus.NOT_FOUND),

    // pp skill
    PROFILE_SKILL_NOT_FOUND(9000, "Skill entry not found", HttpStatus.NOT_FOUND),

    // pp language
    PROFILE_LANGUAGE_NOT_FOUND(10000, "Language entry not found", HttpStatus.NOT_FOUND),

    // pp project
    PROFILE_PROJECT_NOT_FOUND(11000, "Project entry not found", HttpStatus.NOT_FOUND);

    private final int code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(int code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
