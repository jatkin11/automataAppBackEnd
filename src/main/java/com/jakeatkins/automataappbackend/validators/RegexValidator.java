package com.jakeatkins.automataappbackend.validators;

import java.util.ArrayDeque;
import java.util.Deque;

import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.CLOSED_BRACKET;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.EMPTY_SET;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.EPSILON;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.OPEN_BRACKET;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.STAR;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.UNION;
import com.jakeatkins.automataappbackend.exceptions.InvalidRegexException;

/**
 * 
 * RegexValidator
 * 
 * Validation class for user-inputted regex
 * 
 */
public class RegexValidator {
 
    /**
     * ENUM used for the previous character entered in a regex
     * PreviousChar
     */
    private enum PreviousChar{
        NONE,
        VALIDCHAR,
        OPEN_BRACKET,
        CLOSED_BRACKET,
        UNION,
        STAR
    }

    /**
     * Validates a user-inputted string regex
     * 
     * Checks:
     * - regex is not null
     * - regex is not empty string
     * - correct union
     * - correct concatenation
     * - correct bracket ordering e.g. ()
     * - regex only contains A-Z, a-z, 0-9, *, |, (, )
     * 
     * Normalises:
     * - removes all whitespace
     * 
     * Uses switch statement for each char in the regex, whilst also checking the previous character stored in previousChar
     * Uses stack to check correct bracket ordering
     * 
     * @param inputRegex the user inputted regex string
     * @return normalised regex string without whitespace
     * @throws InvalidRegexException if any validation fails
     */
    public static String validate(String inputRegex){
        if(inputRegex == null){
            throw new InvalidRegexException("Regex cannot be null");
        }
        

        String regex = inputRegex.replaceAll("\\s+","");

        if(regex.isBlank()){throw new InvalidRegexException("Regex cannot be blank");}

        Deque<Character> stack = new ArrayDeque<>();
        PreviousChar previousChar = PreviousChar.NONE;

        char[] charArray = regex.toCharArray();
 
        for(char c : charArray){

            switch(c){
                case EPSILON,EMPTY_SET -> {previousChar = PreviousChar.VALIDCHAR;}
                case OPEN_BRACKET -> {
                    stack.push(OPEN_BRACKET);
                    previousChar = PreviousChar.OPEN_BRACKET;
                }
                case CLOSED_BRACKET -> {
                    if(stack.isEmpty()){
                    throw new InvalidRegexException("Invalid regex: incorrect bracket closure");
                    }
                    if(previousChar == PreviousChar.OPEN_BRACKET || previousChar == PreviousChar.UNION){
                        throw new InvalidRegexException("Invalid regex: incorrect bracket closure");
                    }
                    stack.pop();
                    previousChar = PreviousChar.CLOSED_BRACKET;
                }
                case UNION -> {
                    if(previousChar == PreviousChar.NONE || previousChar == PreviousChar.OPEN_BRACKET || previousChar == PreviousChar.UNION){
                    throw new InvalidRegexException("Invalid regex: incorrect union");
                    }
                    previousChar = PreviousChar.UNION;
                }
                case STAR -> {
                    if(previousChar != PreviousChar.VALIDCHAR && previousChar != PreviousChar.CLOSED_BRACKET){
                        throw new InvalidRegexException("Invalid regex: incorrect star");
                    }
                    previousChar = PreviousChar.STAR;
                }
                default -> {
                    if(!String.valueOf(c).matches("^[A-Za-z0-9]$"))
                    {throw new InvalidRegexException("Invalid regex: symbol must be letter or digit");}
                    previousChar = PreviousChar.VALIDCHAR;
                }
            }
        }
        if(previousChar == PreviousChar.UNION || previousChar == PreviousChar.OPEN_BRACKET){throw new InvalidRegexException("Invalid regex: invalid regex ending");}
        if(!stack.isEmpty()){
            throw new InvalidRegexException("Invalid regex: incorrect bracket closure");
        }
        return regex;
    }           
}
