package com.jakeatkins.automataappbackend.algos;

import com.jakeatkins.automataappbackend.exceptions.*;
import com.jakeatkins.automataappbackend.regex.*;
import com.jakeatkins.automataappbackend.validators.RegexValidator;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.EPSILON;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.EMPTY_SET;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.OPEN_BRACKET;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.CLOSED_BRACKET;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.UNION;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.STAR;


public class RegexTokeniser {
    
    private final String regexString;
    private int position = 0;
    private int symbolPosition = 1;
    
    public RegexTokeniser(String regexString){
        String validatedRegex = RegexValidator.validate(regexString);
        this.regexString = validatedRegex;
    }

    public RegexToken tokenise(){
        RegexToken token = unionise();

        if(hasRemaining()){
            throw new InvalidRegexException("Regex Invalid: Remaining symbols in regex string");
        }

        return token;

    }

    private RegexToken unionise(){
        RegexToken left = concatenate();

        while(hasRemaining() && currentChar()== UNION){
            consumeChar(UNION);
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

        while(hasRemaining() && currentChar()== STAR){
            consumeChar(STAR);
            token = new RegexStarred(token);
        }
        return token;
    }

    private RegexToken symbolise(){
        if(!hasRemaining()){
            throw new InvalidRegexException("Regex Invalid: Missing character");
        }
        
        if(currentChar() == OPEN_BRACKET){
            consumeChar(OPEN_BRACKET);
            RegexToken token = unionise();

            if(currentChar() != CLOSED_BRACKET){
                throw new InvalidRegexException("Regex Invalid: Missing Closing Bracket");
            }

            consumeChar(CLOSED_BRACKET);
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
        if(String.valueOf(symbol).matches("^[A-Za-z0-9]$")){
            consumeChar(symbol);        
            
            return new RegexSymbol(symbol,symbolPosition++);
        }

        throw new InvalidRegexException("Regex invalid: Invalid Char: " + symbol);
    }

    private boolean hasRemaining(){
        return this.position < regexString.length();
    }

    private boolean isNewExpression(char c){
        return c==OPEN_BRACKET || String.valueOf(c).matches("^[A-Za-z0-9]$") || c== EPSILON || c == EMPTY_SET ;
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
