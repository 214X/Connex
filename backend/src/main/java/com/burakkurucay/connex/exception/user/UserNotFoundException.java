package com.burakkurucay.connex.exception.user;

import com.burakkurucay.connex.exception.base.BaseException;
import com.burakkurucay.connex.exception.codes.ErrorCode;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(String identifierType, String identifierValue) {
        super(
            "User not found with " + identifierType + ": " + identifierValue,
            ErrorCode.USER_NOT_FOUND
        );
    }
}



