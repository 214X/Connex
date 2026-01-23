package com.burakkurucay.connex.exception.common;

import com.burakkurucay.connex.exception.base.BaseException;
import com.burakkurucay.connex.exception.codes.ErrorCode;
import org.springframework.http.HttpStatus;

public class BusinessException extends BaseException {

    public BusinessException(String message) {
        super(
            message,
            ErrorCode.BUSINESS_ERROR
        );
    }

    public BusinessException(String message, ErrorCode errorCode) {
        super(
            message,
            errorCode
        );
    }

    public BusinessException(String message, HttpStatus status, ErrorCode errorCode) {
        super(
            message,
            errorCode
        );
    }
}

/*
* *** NEREDE KULLANILIR ? ***
* Her şey teknik olarak doğru ama iş kuralı ihlal edildi.
* Örnekler:
*   Email zaten kayıtlı
*   Kullanıcı pasifken işlem yapılmaya çalışıldı
*   Aynı gün ikinci kez başvuru
* */

