package com.burakkurucay.connex.exception.security;

import com.burakkurucay.connex.exception.base.BaseException;
import com.burakkurucay.connex.exception.codes.ErrorCode;

public class SecurityException extends BaseException {

    public SecurityException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
