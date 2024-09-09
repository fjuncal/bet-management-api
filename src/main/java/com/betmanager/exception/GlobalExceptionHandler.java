package com.betmanager.exception;

import com.betmanager.models.dtos.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NoUserFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoUserFoundException(NoUserFoundException ex) {
        log.error(HttpStatus.NOT_FOUND + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(value = UserAlreadyExist.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExist(UserAlreadyExist ex) {
        log.error(HttpStatus.CONFLICT + ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage()));
    }
}
