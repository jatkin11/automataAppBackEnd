package com.jakeatkins.automataappbackend.exceptions;

/**
 * 
 * InvalidWordException
 * 
 *  Custom exception for Invalid Word DTO
 */

public class InvalidWordException extends RuntimeException{
    
    public InvalidWordException(String message){
        super(message);
    }

}
