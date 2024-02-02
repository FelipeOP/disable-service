package com.sgd.disable.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import java.time.LocalDateTime;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<ApiError> buildResponseEntity(
            Exception e,
            HttpServletRequest request,
            HttpStatus httpStatus) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                httpStatus,
                LocalDateTime.now());
        return new ResponseEntity<>(apiError, httpStatus);
    }

    @ExceptionHandler(InvalidFileException.class)
    public ResponseEntity<ApiError> handleException(InvalidFileException e, HttpServletRequest request) {
        return buildResponseEntity(e, request, e.getHttpStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleException(ConstraintViolationException e, HttpServletRequest request) {
        return buildResponseEntity(e, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception e, HttpServletRequest request) {
        return buildResponseEntity(e, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DocumentNotFoundException.class)
    public ResponseEntity<ApiError> handleException(DocumentNotFoundException e, HttpServletRequest request) {
        return buildResponseEntity(e, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiError> handleException(MaxUploadSizeExceededException e, HttpServletRequest request) {
        return buildResponseEntity(e, request, HttpStatus.EXPECTATION_FAILED);
    }
}
