package com.jakeatkins.automataappbackend.validators;

import com.jakeatkins.automataappbackend.dto.*;
import com.jakeatkins.automataappbackend.exceptions.*;

public class RegexStringValidator {
    
    public static void validate(RegexString regex){
        if(regex == null){
            throw new InvalidRegexStringException("RegexString cannot be null");
        }
        if(regex.regex() == null){
            throw new InvalidRegexStringException("RegexString cannot have null string");
        }
    }

}
