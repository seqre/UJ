package uj.jwzp2019.hellospring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uj.jwzp2019.hellospring.exception.HSIllegalArgumentException;

@ControllerAdvice
class IllegalArgumentHandler {

    @ExceptionHandler(HSIllegalArgumentException.class)
    public ResponseEntity<String> interceptHSIllegalArgumentException(HSIllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
