package com.burakkurucay.connex.exception.common;

import com.burakkurucay.connex.exception.base.BaseException;
import com.burakkurucay.connex.exception.codes.ErrorCode;
import org.springframework.http.HttpStatus;

public class TechnicalException extends BaseException {

    public TechnicalException(String message) {
        super(
            message,
            ErrorCode.INTERNAL_ERROR
        );
    }

    public TechnicalException(String message, Throwable cause) {
        super(
            message,
            ErrorCode.INTERNAL_ERROR
        );
        initCause(cause);
    }
}

/*
 * *** NEREDE KULLANILIR ? ***
 * Bu bizim hatamız. Kullanıcı bunu düzeltemez.
 * Örnekler:
 *  DB bağlantısı koptu
 *  External API timeout
 *  JSON parse error
 *  Beklenmeyen null
 * */
