package com.jakeatkins.automataappbackend.exceptions;

/**
 * 
 * InvalidRegexStringException
 * 
 * Custom exception for Invalid RegexString DTO
 */

public class InvalidRegexStringException extends RuntimeException{
        
        public InvalidRegexStringException(String message){
        super(message);
    }
}
