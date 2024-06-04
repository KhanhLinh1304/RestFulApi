package com.example.develop.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class RestExceptionHandle {

    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<ErrorResponse>handleNoHandlerFoundException(  NoHandlerFoundException ex, HttpServletRequest httpServletRequest) {
        ErrorResponse errorResponse = new ErrorResponse("Resource not found",404);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(NotFoundException exc){
        ErrorResponse err = new ErrorResponse();
        err.setStatus(HttpStatus.NOT_FOUND.value());
        err.setMessage(exc.getMessage());
        return new ResponseEntity<>(err,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse err = new ErrorResponse();
        err.setStatus(HttpStatus.BAD_REQUEST.value());
        err.setMessage(ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException exc) {
        ErrorResponse err = new ErrorResponse();
        err.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
        err.setMessage(exc.getMessage());
        return new ResponseEntity<>(err, HttpStatus.METHOD_NOT_ALLOWED);
    }
    @ExceptionHandler(HttpMessageNotReadableException .class)
    public ResponseEntity<ErrorResponse> notValidInput(HttpMessageNotReadableException  e) {
        ErrorResponse err = new ErrorResponse("Invalid Input",400);
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }
}
