package com.jakeatkins.automataappbackend.validators;

import com.jakeatkins.automataappbackend.dto.AutomataWordTest;
import com.jakeatkins.automataappbackend.exceptions.InvalidAutomataWordTestException;

/**
 * 
 * AutomataWordTestValidator
 * 
 * Validator class for an Automata Word-Test
 * 
 */

public class AutomataWordTestValidator {
    
    /**
     * Validates structure and contents of an AutomataWordTest DTO
     * 
     * Checks:
     * - no composite objects are null
     * - the DTO is not null
     * 
     * @param awt
     * @throws InvalidAutomataWordTestException if any validation fails
     */
    public static void validate(AutomataWordTest awt){
        if(awt == null){
            throw new InvalidAutomataWordTestException("AWT cannot be null");
        }
        if(awt.graph() == null){
            throw new InvalidAutomataWordTestException("AWT cannot have null react flow graph");
        }
        if(awt.word() == null){
            throw new InvalidAutomataWordTestException("AWT cannot have null word");
        }
    }
}
