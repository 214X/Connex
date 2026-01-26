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
}

