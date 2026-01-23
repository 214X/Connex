package com.burakkurucay.connex.exception.user;

import com.burakkurucay.connex.exception.base.BaseException;
import com.burakkurucay.connex.exception.codes.ErrorCode;

public class UserAlreadyExistsException extends BaseException {

    public UserAlreadyExistsException(String identifierType, String identifierValue) {
        super(
            "User already exists with " + identifierType + ": " + identifierValue,
            ErrorCode.USER_ALREADY_EXISTS
        );
    }
}



