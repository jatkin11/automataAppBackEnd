package com.jakeatkins.automataappbackend.validators;

import com.jakeatkins.automataappbackend.dto.RegexString;
import com.jakeatkins.automataappbackend.exceptions.InvalidRegexStringException;

/**
 * 
 * RegexStringValidator
 * 
 * Validation class for RegexString DTO
 * 
 */

public class RegexStringValidator {
    
    /**
     * Checks:
     * - Passed RegexString is not null
     * - composite regex string is not null
     * 
     * @param regex RegexString DTO of regex
     * @throws InvalidRegexStringException if any validation fails
     */
    public static void validate(RegexString regex){
        if(regex == null){
            throw new InvalidRegexStringException("RegexString cannot be null");
        }
        if(regex.regex() == null){
            throw new InvalidRegexStringException("RegexString cannot have null string");
        }
    }

}
