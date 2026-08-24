package com.jakeatkins.automataappbackend.exceptions;

/**
 * InvalidWordTestException
 * 
 * Custom exception for Invalid WordTest Data Transfer Object
 * 
 */

public class InvalidWordTestException extends RuntimeException {
    
    public InvalidWordTestException(String message){
        super(message);
    }

}
