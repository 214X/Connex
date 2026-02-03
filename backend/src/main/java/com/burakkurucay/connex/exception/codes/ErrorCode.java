package com.burakkurucay.connex.exception.codes;

public enum ErrorCode {

    // common errors
    VALIDATION_ERROR,
    BUSINESS_ERROR,
    INTERNAL_ERROR,

    // register errors
    PASSWORD_MISSMATCH,
    USER_ALREADY_EXISTS,

    // user errors
    USER_NOT_FOUND,

    // --- Security ---
    AUTH_UNAUTHORIZED, // no / invalid Token
    AUTH_FORBIDDEN, // no authorization
    AUTH_TOKEN_EXPIRED, // auth token expired
    AUTH_INVALID_CREDENTIALS,
    AUTH_ACCOUNT_LOCKED, // account suspended
    AUTH_ACCOUNT_DISABLED, // account inactive

    // profile
    PROFILE_ALREADY_EXISTS,
    PROFILE_NOT_FOUND
}
