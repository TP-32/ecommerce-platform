package com.tp32.ecommerceplatform.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /*
     * Thrown if an invalid input is given, for example a duplicate email.
     */
    @ExceptionHandler(InputException.class)
    public ResponseEntity<String> handleInputException(InputException exception) {
        String response =
                  "<header>"
                + "<h1><span>An exception has occurred: "
                + "</span></h1><h2 style='color:red;'>" + exception.getMessage() + "</h2>"
                + "</header>";

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /*
     * We are taking over the control of the Exception class, so we must declare a generic Exception one too.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*
     * Stores all errors, including name and message, into a Map, and returns it within a Response Entity object.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }    
}
