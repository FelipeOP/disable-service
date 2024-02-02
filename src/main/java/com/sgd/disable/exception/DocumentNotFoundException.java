package com.sgd.disable.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DocumentNotFoundException extends Exception {

    public DocumentNotFoundException(String message) {
        super(message);
    }
}
