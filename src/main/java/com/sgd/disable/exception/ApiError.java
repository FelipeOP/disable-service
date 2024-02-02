package com.sgd.disable.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiError {

    private String path;
    private String message;
    private HttpStatus statusCode;
    private LocalDateTime localDateTime;

}
