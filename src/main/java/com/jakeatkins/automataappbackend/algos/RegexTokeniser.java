package com.jakeatkins.automataappbackend.algos;

import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.CLOSED_BRACKET;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.EMPTY_SET;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.EPSILON;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.OPEN_BRACKET;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.STAR;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.UNION;
import com.jakeatkins.automataappbackend.exceptions.InvalidRegexException;
import com.jakeatkins.automataappbackend.regex.RegexConcat;
import com.jakeatkins.automataappbackend.regex.RegexEmptySet;
import com.jakeatkins.automataappbackend.regex.RegexEpsilon;
import com.jakeatkins.automataappbackend.regex.RegexStarred;
import com.jakeatkins.automataappbackend.regex.RegexSymbol;
import com.jakeatkins.automataappbackend.regex.RegexToken;
import com.jakeatkins.automataappbackend.regex.RegexUnion;
import com.jakeatkins.automataappbackend.validators.RegexValidator;

/**
 * 
 * RegexTokeniser
 * 
 * A recursive descent parser adapted from pseudocode found in report Chapter 4.1A (Course Project 2 Regular Expressions, 2017)
 */
public class RegexTokeniser {
    
    private final String regexString; //regex string being processed
    private int position = 0; //current position in regex string
    private int symbolPosition = 1; //position only used for Glushkov algo
    
    /**
     * Constructor for new RegexTokeniser instance
     * 
     * - validates regex string using RegexValidator
     * - sets instance regexString to the validated regex string
     * 
     * @param regexString regex string to tokenise into RegexToken tree
     */
    public RegexTokeniser(String regexString){
        String validatedRegex = RegexValidator.validate(regexString);
        this.regexString = validatedRegex;
    }

    /**
     * Start the recursion process (unionise > concatenate > star > symbolise)
     * 
     * - calls unionise as token
     * - checks regex processed by throwing if regex string still has remaining characters
     *  
     * @return RegexToken tree
     * @throws InvalidRegexException if string was processed incorrectly i.e. has characters remaining at end of recursion
     */
    public RegexToken tokenise(){
        RegexToken token = unionise();

        if(hasRemaining()){
            throw new InvalidRegexException("Regex Invalid: Remaining symbols in regex string");
        }

        return token;

    }

    /**
     * Unionises expressions e.g. a | b becomes RegexUnion(RegexSymbol('a'), RegexSymbol('b'))
     * 
     * - call concatenate for left token
     * if regex still has chars remaining and the current char is a union (i.e. a union is found):
     *      - consumes the union
     *      - calls concatenate as the right side of the union
     *      - left token becomes RegexUnion of left and right tokens
     * 
     * @return left token
     * @throws InvalidRegexException if no more chars to consume after a union consumed i.e. an invalid union placement
     */
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


    /**
     * Concatenates expressions e.g. ab becomes RegexConcat(RegexSymbol('a'), RegexSymbol('b'))
     * 
     * - call star() for left token
     *      while regex still to process and the current char is a new expression (i.e. new expression found):
     *              - calls star() for right token
     *              - left token becomes concat of left & right tokens
     * @return left token
     */
    private RegexToken concatenate(){
        RegexToken left = star();
        while(hasRemaining() && isNewExpression(currentChar())){
            RegexToken right = star();
            left = new RegexConcat(left, right);
        }
        return left;
    }

    /**
     * Wraps expression with a star e.g a* becomes RegexStarred(RegexSymbol('a'))
     * 
     * - calls symbolise as token
     * - while current char is a star and regex remaining (i.e. star found):
     *      - consumes star
     *      - token becomes RegexStarred of token
     *
     * @return token
     */
    private RegexToken star(){
        RegexToken token = symbolise();

        while(hasRemaining() && currentChar()== STAR){
            consumeChar(STAR);
            token = new RegexStarred(token);
        }
        return token;
    }

    /**
     * Symbolises expression. e.g. 'a' becomes RegexSymbol('a') 
     * 
     * processes:
     * - bracketed expressions by consuming open bracket, calling unionise() as the token, consuming closed bracket 
     * - single chars:
     *      - returning RegexEpsilon for 'ε'
     *      - returning RegexEmptySet for '∅'
     *      - returning RegexSymbol for A-Z,a-z,0-9
     * 
     * - advances current position when consumeChar is called
     * 
     * @return RegexToken as described in processes e.g. RegexEpsilon for ε, etc.
     * @throws InvalidRegexException if regex does not have characters remaining, incorrect bracketing or invalid character
     */
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

    /**
     * Checks regex string has characters remaining by checking current position is less than regex string length 
     * 
     * @return true if regex string has characters left to process
     */
    private boolean hasRemaining(){
        return this.position < regexString.length();
    }


    /**
     * Checks if expression is the start of an expression i.e. is an open bracket or a symbol (a-z,A-Z,0-9,'ε','∅')
     * 
     * @param c character in regex
     * @return true if new expression 
     */
    private boolean isNewExpression(char c){
        return c==OPEN_BRACKET || String.valueOf(c).matches("^[A-Za-z0-9]$") || c== EPSILON || c == EMPTY_SET ;
    }
 

    /**
     * consumes a character in the regex string by moving the current position +1, 
     * 
     * @param c character to consume
     * @throws InvalidRegexException if the character at the current position is not character to be consumed or no more regex to process
     */
    private void consumeChar(char c){
        if(!hasRemaining() || currentChar()!=c ){
            throw new InvalidRegexException("Regex Invalid: Invalid consumption");
        }
        position++;
    }


    /**
     * Gets the char at the current position  
     * @return char at current position
     * @throws InvalidRegexException if no more regex to process
     */
    private char currentChar(){
        if(!hasRemaining()){
            throw new InvalidRegexException("Regex Invalid: Missing character");
        }
        return regexString.charAt(position);
    }

}
