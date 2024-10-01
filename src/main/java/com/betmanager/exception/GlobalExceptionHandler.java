package com.betmanager.exception;

import com.betmanager.models.dtos.ApiResponse;
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
    public ResponseEntity<ApiResponse<String>> handleNoUserFoundException(NoUserFoundException ex) {
        log.error(HttpStatus.NOT_FOUND + ex.getMessage(), ex);
        ApiResponse<String> response = new ApiResponse<>("error", null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(value = UserAlreadyExist.class)
    public ResponseEntity<ApiResponse<String>> handleUserAlreadyExist(UserAlreadyExist ex) {
        log.error(HttpStatus.CONFLICT + ex.getMessage(), ex);
        ApiResponse<String> response = new ApiResponse<>("error", null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler({AuthenticationException.class, InternalAuthenticationServiceException.class})
    public ResponseEntity<ApiResponse<String>> handleAuthenticationException(AuthenticationException ex) {
        log.error(HttpStatus.NOT_FOUND + ex.getMessage(), ex);
        ApiResponse<String> response = new ApiResponse<>("error", null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(value = NoBetFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNoBetFoundException(NoBetFoundException ex) {
        log.error(HttpStatus.NOT_FOUND + ex.getMessage(), ex);
        ApiResponse<String> response = new ApiResponse<>("error", null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiResponse<Map<String, String>> response = new ApiResponse<>("error", errors, "Validation failed");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<String>> handleBadRequestException(BadRequestException ex) {
        log.error(HttpStatus.BAD_REQUEST + ex.getMessage(), ex);
        ApiResponse<String> response = new ApiResponse<>("error", null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<String>> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        log.error(HttpStatus.BAD_REQUEST + ex.getMessage(), ex);
        ApiResponse<String> response = new ApiResponse<>("error", null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ClassNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleClassNotFoundException(ClassNotFoundException ex) {
        log.error("ClassNotFoundException: " + ex.getMessage(), ex);
        ApiResponse<String> response = new ApiResponse<>("error", null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error(HttpStatus.INTERNAL_SERVER_ERROR + ex.getMessage(), ex);
        ApiResponse<String> response = new ApiResponse<>("error", null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<String>> handleRuntimeException(RuntimeException ex) {
        log.error(HttpStatus.INTERNAL_SERVER_ERROR + ex.getMessage(), ex);
        ApiResponse<String> response = new ApiResponse<>("error", null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(CustomUnauthorizedException.class)
    public ResponseEntity<ApiResponse<String>> handleCustomUnauthorizedException(CustomUnauthorizedException ex) {
        log.error(HttpStatus.UNAUTHORIZED + ex.getMessage(), ex);
        ApiResponse<String> response = new ApiResponse<>("error", null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<String>> handleAccessDeniedException(AccessDeniedException ex) {
        log.error(HttpStatus.UNAUTHORIZED + ex.getMessage(), ex);
        ApiResponse<String> response = new ApiResponse<>("error", null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
