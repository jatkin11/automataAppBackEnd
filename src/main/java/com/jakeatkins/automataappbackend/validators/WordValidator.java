package com.jakeatkins.automataappbackend.validators;

import com.jakeatkins.automataappbackend.exceptions.*;

public class WordValidator {
    
    public static void validate(String word){
        if(word == null){
            throw new InvalidWordException("Word cannot be null");
        }
                
        if(!word.matches("[a-zA-Z0-9]*")){
            throw new InvalidWordException("Invalide word: must be A-Z, a-z, 0-9");
        }
    }
}
