package com.javaguru.studentservice.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorDto> handle(Exception exception) {
        if (exception instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) exception;
            String message = ex.getBindingResult().getFieldErrors().stream()
                    .map(error -> error.getField() + " : " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));

            return ResponseEntity.badRequest()
                    .body(new ErrorDto(message));
        }

        if (exception instanceof ItemNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorDto(exception.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto(exception.getMessage()));

    }
}
