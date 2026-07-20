package com.jakeatkins.automataappbackend.validators;

import com.jakeatkins.automataappbackend.dto.AutomataWordTest;
import com.jakeatkins.automataappbackend.exceptions.*;

public class AutomataWordTestValidator {
    
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
