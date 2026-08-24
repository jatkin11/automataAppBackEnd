package com.jakeatkins.automataappbackend.exceptions;

/**
 * 
 * InvalidReactFlowGraphException
 * 
 * Custom exception for Invalid ReactFlowGraph DTO
 */
public class InvalidReactFlowGraphException extends RuntimeException {
    
        public InvalidReactFlowGraphException(String message){
        super(message);
    }


}
