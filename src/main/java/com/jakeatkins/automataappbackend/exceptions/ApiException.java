package com.jakeatkins.automataappbackend.exceptions;

import java.time.Instant;

/**
 * ApiException
 * 
 * DTO for API exception used for storing information from a thrown error in the GobalExceptionHandler to be attached to an HTTP response
 * 
 * @param error error
 * @param message error message
 * @param status HTTP status
 * @param timestamp time of occurance
 */

public record ApiException(String error, String message, int status, Instant timestamp ) {
    
}
