package org.example.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandier {
     @ExceptionHandler(value = RuntimeException.class)
     ResponseEntity<String> handLingRuntimeException(RuntimeException exception) {
         return ResponseEntity.badRequest().body(exception.getMessage());

     }
}
