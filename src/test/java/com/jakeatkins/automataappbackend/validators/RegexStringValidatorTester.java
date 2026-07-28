package com.jakeatkins.automataappbackend.validators;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.jakeatkins.automataappbackend.exceptions.InvalidRegexStringException;
import static com.jakeatkins.automataappbackend.utilities.TestRegexStringCreator.RegexStringWithNullRegex;
import static com.jakeatkins.automataappbackend.utilities.TestRegexStringCreator.simpleValidRegexString;

public class RegexStringValidatorTester {
    

 @Test
 void acceptsValidRegexString(){
    assertDoesNotThrow(()->RegexStringValidator.validate(simpleValidRegexString()));
 }

 @Test
 void rejectsNullRegexString(){
    assertThrows(InvalidRegexStringException.class, ()-> RegexStringValidator.validate(null));    
 }

 @Test
 void rejectsRegexStringWithNullRegex(){
    assertThrows(InvalidRegexStringException.class, ()-> RegexStringValidator.validate(RegexStringWithNullRegex()));    
 }

}
