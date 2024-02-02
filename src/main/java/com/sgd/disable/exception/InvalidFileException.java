package com.sgd.disable.exception;

import org.springframework.http.HttpStatus;

public class InvalidFileException extends Exception {

    private final HttpStatus httpStatus;

    public InvalidFileException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
