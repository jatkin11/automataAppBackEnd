package com.jakeatkins.automataappbackend.exceptions;

/**
 * 
 * InvalidAutomataWordTestException
 * 
 * Custom exception for Invalid AutomataWordTest DTO
 */

public class InvalidAutomataWordTestException extends RuntimeException {
    
        public InvalidAutomataWordTestException(String message){
        super(message);
    }
}
