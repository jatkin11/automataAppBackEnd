package com.jakeatkins.automataappbackend.algos;

import com.jakeatkins.automataappbackend.regex.RegexToken;
import com.jakeatkins.automataappbackend.regex.*;

public class RegexTokeniser {
    
    private String regexString;
    private int position = 0;
    
    //NEED TO ADD VALIDATION
    public RegexTokeniser(String regexString){
        this.regexString = regexString;
    }

    //NEED TO ADD VALIDATION
    public RegexToken tokenise(){
        return unionise();
    }

    //NEED TO ADD VALIDATION
    private RegexToken unionise(){
        RegexToken left = concatenate();

        while(currentChar()=='|'){
            consumeChar('|');
            RegexToken right = concatenate();
            left = RegexUnion(left,right);
        }
        return left;
    }

    //NEED TO ADD VALIDATION
    private RegexToken concatenate(){

    }

    //NEED TO ADD VALIDATION
    private RegexToken star(){


    }

    //NEED TO ADD VALIDATION
    private RegexToken symbolise(){

    }

    //NEED TO ADD VALIDATION
    private boolean hasRemaining(){
        return this.position < regexString.length();
    }

    //NEED TO ADD VALIDATION
    private boolean isNewExpression(char c){
        return c=='(' || Character.isLetterOrDigit(c);
    }

    //NEED TO ADD VALIDATION
    private void consumeChar(char c){
        if(!hasRemaining() || currentChar()!=c){
            throw new IllegalArgumentException("invalid consumption");
        }
        position++;
    }

    //NEED TO ADD VALIDATION
    private char currentChar(){
        return regexString.charAt(position);
    }

}
