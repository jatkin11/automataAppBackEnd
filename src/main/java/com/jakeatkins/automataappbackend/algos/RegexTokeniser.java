package com.jakeatkins.automataappbackend.algos;

import com.jakeatkins.automataappbackend.exceptions.InvalidRegexException;
import com.jakeatkins.automataappbackend.regex.*;
import com.jakeatkins.automataappbackend.validators.RegexValidator;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.EPSILON;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.EMPTY_SET;

public class RegexTokeniser {
    
    private final String regexString;
    private int position = 0;
    private int symbolPosition = 1;
    
    public RegexTokeniser(String regexString){
        RegexValidator.validate(regexString);
        this.regexString = regexString;
    }

    public RegexToken tokenise(){
        return unionise();
    }

    private RegexToken unionise(){
        RegexToken left = concatenate();

        while(hasRemaining() && currentChar()=='|'){
            consumeChar('|');
            if(!hasRemaining()){
                throw new InvalidRegexException("Regex Invalid: Missing character after'|'");
            }
            RegexToken right = concatenate();
            left = new RegexUnion(left,right);
        }
        return left;
    }

    private RegexToken concatenate(){
        RegexToken left = star();
        while(hasRemaining() && isNewExpression(currentChar())){
            RegexToken right = star();
            left = new RegexConcat(left, right);
        }
        return left;
    }

    private RegexToken star(){
        RegexToken token = symbolise();

        while(hasRemaining() && currentChar()=='*'){
            consumeChar('*');
            token = new RegexStarred(token);
        }
        return token;
    }

    private RegexToken symbolise(){
        if(!hasRemaining()){
            throw new InvalidRegexException("Regex Invalid: Missing character");
        }
        
        if(currentChar() == '('){
            consumeChar('(');
            RegexToken token = unionise();
            consumeChar(')');
            return token;
        }

        char symbol = currentChar();

        if(symbol == EPSILON){
            consumeChar(symbol);
            return new RegexEpsilon();
        }

        if(symbol == EMPTY_SET){
            consumeChar(symbol);
            return new RegexEmptySet();
        }
        consumeChar(symbol);
        return new RegexSymbol(symbol,symbolPosition++);
    }

    private boolean hasRemaining(){
        return this.position < regexString.length();
    }

    private boolean isNewExpression(char c){
        return c=='(' || Character.isLetterOrDigit(c) || c== EPSILON || c == EMPTY_SET ;
    }
 
    private void consumeChar(char c){
        if(!hasRemaining() || currentChar()!=c ){
            throw new InvalidRegexException("Regex Invalid: Invalid consumption");
        }
        position++;
    }

    private char currentChar(){
        if(!hasRemaining()){
            throw new InvalidRegexException("Regex Invalid: Missing character");
        }
        return regexString.charAt(position);
    }

}
