package com.jakeatkins.automataappbackend.validators;

import com.jakeatkins.automataappbackend.dto.RegexWordTest;
import com.jakeatkins.automataappbackend.exceptions.InvalidRegexWordTestException;

/**
 * 
 * RegexWordTestValidator
 * 
 * Validation class for RegexWordTest DTO
 * 
 */
public class RegexWordTestValidator {
    
    /**
     * Checks:
     * - RegexWordTest passed is not null
     * - composite regex string is not null
     * - composite word string is not null
     * 
     * @param rwt
     * @throws InvalidRegexWordTestException if any validation fails
     */
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
