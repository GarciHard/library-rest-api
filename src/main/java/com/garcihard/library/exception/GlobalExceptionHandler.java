package com.garcihard.library.exception;

import com.garcihard.library.exception.custom.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleBookNotFound(BookNotFoundException bnfe) {
        return new ResponseEntity<>(bnfe.getMessage(), HttpStatus.NOT_FOUND);
    }
}
