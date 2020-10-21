package com.rigby.jazzit.config;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.rigby.jazzit.config.exception.BadRequestException;
import com.rigby.jazzit.config.exception.NotFoundException;
import com.rigby.jazzit.config.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;


@ControllerAdvice(annotations = RestController.class)
public class ExceptionConfig {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> unauthorizedException(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFoundException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequestException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, InvalidFormatException.class})
    public ResponseEntity<?> invalidFormatException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid formatted values entered");
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<?> methodArgumentNotValidException(Exception e) {
//        List<ObjectError> errors = ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors();
//
//        Object [] objects = errors.get(0).getArguments();
//        String field = ((DefaultMessageSourceResolvable) objects[0]).getDefaultMessage();
//
////        String errorMessage = ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors().get(0).getDefaultMessage();
////        String errorField = ((DefaultMessageSourceResolvable)((MethodArgumentNotValidException) e).getBindingResult().getAllErrors().get(0).getArguments()[0]).getDefaultMessage();
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Field: " + field + " error: " + errors.get(0).getDefaultMessage());
////        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("One or more fields aren't valid");
//    }

}
