package com.jakeatkins.automataappbackend.validators;

import com.jakeatkins.automataappbackend.exceptions.InvalidWordException;

/**
 * 
 * WordValidator
 * 
 * Validation class of user-inputted word to test
 * 
 */

public class WordValidator {
    
    /**
     * Checks:
     * - word is not null
     * - word chars are A-Z,a-z,0-9
     * - allows empty string as it represents Automata langugage "ε"
     * 
     * @param word user-inputted word to test
     * @throws InvalidWordException, if any validation fails
     */
    public static void validate(String word){
        if(word == null){
            throw new InvalidWordException("Word cannot be null");
        }
                
        if(!word.matches("[a-zA-Z0-9]*")){
            throw new InvalidWordException("Invalid word: must be A-Z, a-z, 0-9");
        }
    }
}
