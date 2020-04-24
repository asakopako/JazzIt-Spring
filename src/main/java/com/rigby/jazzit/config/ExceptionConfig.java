package com.rigby.jazzit.config;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.rigby.jazzit.config.exception.BadRequestException;
import com.rigby.jazzit.config.exception.ForbiddenException;
import com.rigby.jazzit.config.exception.NotFoundException;
import com.rigby.jazzit.config.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionConfig {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> unauthorizedException(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> forbiddenException(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFoundException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequestException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<?> invalidFormatException(Exception e) {
        String field = "BUSCAR EL NOMBRE DEPURANDO la excepcion"; // todo buscarlo
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Field " + field + " error: " + e.getCause().getCause().getMessage());
    }
}
