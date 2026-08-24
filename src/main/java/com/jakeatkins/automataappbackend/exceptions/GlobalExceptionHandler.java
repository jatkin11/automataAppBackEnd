package com.jakeatkins.automataappbackend.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 
 * GlobalExceptionHandler
 * 
 * Global exception handling for all API requests
 * 
 * For known custom exception classes, custom exception turned into ApiException which is attached to HTTP 400 response:
 * - "Bad Request"
 * - the error message
 * - HTTP status: BAD_REQUEST
 * - timestamp
 * 
 * For unknown general exceptions, turned into ApiExpception which is attached to HTTP 500 response:
 * - "Internal Server Error",
 * - the error message
 * - HTTP status: INTERNAL_SERVER_ERROR
 * - timestamp
 * 
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Custom exception Handler for known exceptions
     * 
     * @param e the execption
     * @return HTTP 400 resposne with ApiException body
     */
    @ExceptionHandler({InvalidAutomataException.class,
                    InvalidReactFlowGraphException.class, 
                        InvalidRegexException.class,
                        InvalidRegexTokenException.class,
                        InvalidWordTestException.class,
                        InvalidWordException.class,
                        InvalidAutomataWordTestException.class,
                        InvalidRegexStringException.class,
                        InvalidRegexWordTestException.class})
    public ResponseEntity<ApiException> badRequest(RuntimeException e){
        ApiException apiException = new ApiException("Bad Request", e.getMessage(), HttpStatus.BAD_REQUEST.value(), Instant.now());

        return ResponseEntity.badRequest().body(apiException);
    }

    /**
     * General unexpected exception handler (for non-custom exceptions) i.e. a catch-all
     * @param e the exception
     * @return HTTP 500 response with ApiException body
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiException> unexpectedException(Exception e){
        ApiException apiException = new ApiException("Internal Server Error", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), Instant.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiException);
    }
    
}
