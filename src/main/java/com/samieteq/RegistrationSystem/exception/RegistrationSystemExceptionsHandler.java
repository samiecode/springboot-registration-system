package com.samieteq.RegistrationSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.time.LocalDateTime;

@ControllerAdvice
public class RegistrationSystemExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Object> handleException(DuplicateResourceException e){
        ErrorDetail errorDetail = new ErrorDetail( e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorDetail, e.getHttpStatus());
    }

    @ExceptionHandler(RegistrationSystemException.class)
    public ResponseEntity<Object> handleExceptions(RegistrationSystemException e){
        ErrorDetail errorDetail = new ErrorDetail(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorDetail, e.getHttpStatus());
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<?> handleExceptions(SQLException e){
        ErrorDetail errorDetail = new ErrorDetail(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleExceptions(IllegalStateException e){
        ErrorDetail errorDetail = new ErrorDetail(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    //Handle UserNotFoundException
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleExceptions(UsernameNotFoundException e){
        ErrorDetail errorDetail = new ErrorDetail(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

}
