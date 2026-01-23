package com.burakkurucay.connex.exception.handler;

import com.burakkurucay.connex.dto.error.ApiErrorResponse;
import com.burakkurucay.connex.exception.base.BaseException;
import com.burakkurucay.connex.exception.codes.ErrorCode;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Map<ErrorCode, HttpStatus> STATUS_MAP = Map.of(
        ErrorCode.USER_NOT_FOUND, HttpStatus.NOT_FOUND,
        ErrorCode.USER_ALREADY_EXISTS, HttpStatus.CONFLICT,
        ErrorCode.VALIDATION_ERROR, HttpStatus.BAD_REQUEST,
        ErrorCode.BUSINESS_ERROR, HttpStatus.BAD_REQUEST,
        ErrorCode.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR
    );

    // Handle domain / business exceptions
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiErrorResponse> handleBaseException(
        BaseException ex,
        HttpServletRequest request
    ) {
        HttpStatus status = STATUS_MAP.getOrDefault(
            ex.getErrorCode(),
            HttpStatus.INTERNAL_SERVER_ERROR
        );

        ApiErrorResponse response = new ApiErrorResponse(
            status.value(),
            ex.getErrorCode().name(),
            ex.getMessage(),
            Instant.now(),
            request.getRequestURI(),
            null
        );

        return ResponseEntity.status(status).body(response);
    }

    // Handle Bean Validation (DTO validation)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpServletRequest request
    ) {
        List<String> details = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(FieldError::getDefaultMessage)
            .toList();

        ApiErrorResponse response = new ApiErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            ErrorCode.VALIDATION_ERROR.name(),
            "Validation failed",
            Instant.now(),
            request.getRequestURI(),
            details
        );

        return ResponseEntity.badRequest().body(response);
    }

    // Handle unexpected / unhandled exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpectedException(
        Exception ex,
        HttpServletRequest request
    ) {
        ApiErrorResponse response = new ApiErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            ErrorCode.INTERNAL_ERROR.name(),
            "Unexpected error occurred",
            Instant.now(),
            request.getRequestURI(),
            List.of()
        );

        return ResponseEntity.internalServerError().body(response);
    }
}
