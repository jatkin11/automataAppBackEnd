package com.jakeatkins.automataappbackend.exceptions;

/**
 * 
 * InvalidRegexTokenException
 * 
 * Custom exception for Invalid RegexToken DTO
 */


public class InvalidRegexTokenException extends RuntimeException{
    
        public InvalidRegexTokenException(String message){
        super(message);
    }

}
