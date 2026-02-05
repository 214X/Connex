package com.burakkurucay.connex.exception.base;

import com.burakkurucay.connex.exception.codes.ErrorCode;

public abstract class BaseException extends RuntimeException {

    private final ErrorCode errorCode;

    protected BaseException(
            String message,
            ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
