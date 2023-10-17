package com.zhugalcf.superkassa.controller.handler;

import com.zhugalcf.superkassa.exception.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<String> handleDataNotFoundException(DataNotFoundException e) {
        log.error("Entity not found exception occurred", e);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleBusinessException(Exception e) {
        log.error("Internal Error", e);
        return ResponseEntity
                .status(HttpStatus.I_AM_A_TEAPOT)
                .body(e.getMessage());
    }
}
