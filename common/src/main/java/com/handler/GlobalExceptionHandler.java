package com.handler;

import com.exception.BusinessException;
import com.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;

import static com.exception.message.CommonExceptionMessage.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * javax.validation.Valid or @Validated binding 예외 처리 handler
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);

        return new ResponseEntity<>(ErrorResponse.of(INVALID_INPUT_VALUE, e.getBindingResult()), HttpStatus.BAD_REQUEST);
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 예외 처리 handler
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException", e);

        return new ResponseEntity<>(ErrorResponse.of(METHOD_NOT_ALLOWED), HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Authentication 예외 처리 handler
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        log.error("handleAccessDeniedException", e);

        return new ResponseEntity<>(ErrorResponse.of(HANDLE_ACCESS_DENIED), HttpStatus.UNAUTHORIZED);
    }

    /**
     * MethodArgumentTypeMismatchException handler
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("handleMethodArgumentTypeMismatchException", e);

        return new ResponseEntity<>(ErrorResponse.of(HANDLE_METHOD_ARGUMENT_TYPE_MISMATCH), HttpStatus.BAD_REQUEST);
    }

    /**
     * business 예외 처리 handler
     */
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException e) {
        log.error("handleBusinessException", e);

        return new ResponseEntity<>(ErrorResponse.of(e.getErrorMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * server error handler
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("handleException", e);

        return new ResponseEntity<>(ErrorResponse.of(INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
