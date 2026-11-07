package com.example.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<?> handleNotFound(
            AccountNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error(ex.getMessage(), 404));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleBadRequest(
            IllegalArgumentException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(error(ex.getMessage(), 400));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<?> handleDatabaseError(
            DataAccessException ex) {

        // Log the full exception internally.
        return ResponseEntity.status(
                HttpStatus.INTERNAL_SERVER_ERROR
        ).body(error(
                "Database operation failed",
                500
        ));
    }

    private ErrorResponse error(
            String message,
            int status) {

        return new ErrorResponse(
                status,
                message,
                LocalDateTime.now()
        );
    }

    public record ErrorResponse(
            int status,
            String message,
            LocalDateTime timestamp) {
    }
}
