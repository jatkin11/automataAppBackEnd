package com.jakeatkins.automataappbackend.exceptions;

/**
 * 
 * InvalidRegexException
 * 
 * Custom exception for Invalid Regex DTO
 */

public class InvalidRegexException extends RuntimeException {
    
        public InvalidRegexException(String message){
        super(message);
    }

}
