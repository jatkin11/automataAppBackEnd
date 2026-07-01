package com.jakeatkins.automataappbackend.algos;

import com.jakeatkins.automataappbackend.regex.RegexConcat;
import com.jakeatkins.automataappbackend.regex.RegexStarred;
import com.jakeatkins.automataappbackend.regex.RegexSymbol;
import com.jakeatkins.automataappbackend.regex.RegexToken;
import com.jakeatkins.automataappbackend.regex.RegexUnion;

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
            left = new RegexUnion(left,right);
        }
        return left;
    }

    //NEED TO ADD VALIDATION
    private RegexToken concatenate(){
        RegexToken left = star();
        while(hasRemaining() && isNewExpression(currentChar())){
            RegexToken right = star();
            left = new RegexConcat(left, right);
        }
        return left;
    }

    //NEED TO ADD VALIDATION
    private RegexToken star(){
        RegexToken token = symbolise();

        while(hasRemaining() && currentChar()=='*'){
            consumeChar('*');
            token = new RegexStarred(token);
        }
        return token;
    }

    //NEED TO ADD VALIDATION
    private RegexToken symbolise(){
        if(currentChar() == '('){
            consumeChar('(');
            RegexToken token = unionise();
            consumeChar(')');
            return token;
        }

        char symbol = currentChar();
        consumeChar(symbol);
        return new RegexSymbol(symbol);
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
