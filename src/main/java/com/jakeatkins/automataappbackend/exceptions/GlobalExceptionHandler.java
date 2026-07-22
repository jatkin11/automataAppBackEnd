package com.jakeatkins.automataappbackend.exceptions;

import java.time.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiException> unexpectedException(Exception e){
        ApiException apiException = new ApiException("Internal Server Error", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), Instant.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiException);
    }
    
}
