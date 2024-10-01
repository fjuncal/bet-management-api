package com.betmanager.exception;

import com.betmanager.models.dtos.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NoUserFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoUserFoundException(NoUserFoundException ex) {
        log.error(HttpStatus.NOT_FOUND + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(value = UserAlreadyExist.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExist(UserAlreadyExist ex) {
        log.error(HttpStatus.CONFLICT + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage()));
    }

    @ExceptionHandler({AuthenticationException.class, InternalAuthenticationServiceException.class})
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        log.error(HttpStatus.NOT_FOUND + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(value = NoBetFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoBetFoundException(NoBetFoundException ex) {
        log.error(HttpStatus.NOT_FOUND + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        log.error(HttpStatus.BAD_REQUEST + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        log.error(HttpStatus.BAD_REQUEST + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(ClassNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleClassNotFoundException(ClassNotFoundException ex) {
        log.error("ClassNotFoundException: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error occurred."));
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error(HttpStatus.INTERNAL_SERVER_ERROR + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        log.error(HttpStatus.INTERNAL_SERVER_ERROR + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
    }
    @ExceptionHandler(CustomUnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleCustomUnauthorizedException(CustomUnauthorizedException ex) {
        log.error(HttpStatus.UNAUTHORIZED + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        log.error(HttpStatus.UNAUTHORIZED + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage()));
    }
}
