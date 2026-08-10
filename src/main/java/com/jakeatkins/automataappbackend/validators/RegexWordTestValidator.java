package com.jakeatkins.automataappbackend.validators;

import com.jakeatkins.automataappbackend.dto.RegexWordTest;
import com.jakeatkins.automataappbackend.exceptions.InvalidRegexWordTestException;

public class RegexWordTestValidator {
    
    public static void validate(RegexWordTest rwt){
        if(rwt == null){
            throw new InvalidRegexWordTestException("RWT cannot be null");
        }
        if(rwt.regex() == null){
            throw new InvalidRegexWordTestException("RWT cannot have null regex string");
        }
        if(rwt.word() == null){
            throw new InvalidRegexWordTestException("RWT cannot have null word");
        }
    }

}
