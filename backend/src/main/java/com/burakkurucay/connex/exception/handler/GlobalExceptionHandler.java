package com.burakkurucay.connex.exception.handler;

import com.burakkurucay.connex.dto.common.ApiResponse;
import com.burakkurucay.connex.exception.base.BaseException;
import com.burakkurucay.connex.exception.codes.ErrorCode;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Map<ErrorCode, HttpStatus> STATUS_MAP = Map.of(
            ErrorCode.USER_NOT_FOUND, HttpStatus.NOT_FOUND,
            ErrorCode.USER_ALREADY_EXISTS, HttpStatus.CONFLICT,
            ErrorCode.VALIDATION_ERROR, HttpStatus.BAD_REQUEST,
            ErrorCode.BUSINESS_ERROR, HttpStatus.BAD_REQUEST,
            ErrorCode.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);

    // Handle domain / business exceptions
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<Void>> handleBaseException(BaseException ex) {
        HttpStatus status = STATUS_MAP.getOrDefault(
                ex.getErrorCode(),
                HttpStatus.INTERNAL_SERVER_ERROR);

        ApiResponse<Void> response = ApiResponse.error(
                ex.getErrorCode().name(),
                ex.getMessage());

        return ResponseEntity.status(status).body(response);
    }

    // Handle Bean Validation (DTO validation)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {
        List<String> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        ApiResponse<Void> response = ApiResponse.error(
                ErrorCode.VALIDATION_ERROR.name(),
                "Validation failed",
                details);

        return ResponseEntity.badRequest().body(response);
    }

    // Handle unexpected / unhandled exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnexpectedException(Exception ex) {
        ApiResponse<Void> response = ApiResponse.error(
                ErrorCode.INTERNAL_ERROR.name(),
                "Unexpected error occurred");

        return ResponseEntity.internalServerError().body(response);
    }
}
