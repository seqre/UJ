package uj.jwzp2019.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uj.jwzp2019.service.exceptions.HTIllegalArgumentException;
import uj.jwzp2019.service.exceptions.HTRestClientException;

@ControllerAdvice
class HelloTestHandler {

    @ExceptionHandler(HTIllegalArgumentException.class)
    public ResponseEntity<String> interceptIllegalArgumentException(HTIllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(HTRestClientException.class)
    public ResponseEntity<String> interceptRestClientException(HTRestClientException ex) {
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(ex.getMessage());
    }
}
