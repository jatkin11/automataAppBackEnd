package com.jakeatkins.automataappbackend.validators;

public class WordValidator {
    
    public static boolean validateWord(String word){
        return word != null && word.matches("[a-zA-Z0-9]*");
    }
}
