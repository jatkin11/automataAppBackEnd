package com.jakeatkins.automataappbackend.exceptions;

/**
 * 
 * InvalidRegexWordTestException
 * 
 * Custom exception for Invalid Regex Word Test DTO
 */

public class InvalidRegexWordTestException extends RuntimeException {
        
    public InvalidRegexWordTestException(String message){
        super(message);
    }
}
