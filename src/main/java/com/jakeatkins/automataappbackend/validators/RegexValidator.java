package com.jakeatkins.automataappbackend.validators;

import java.util.ArrayDeque;
import java.util.Deque;
import com.jakeatkins.automataappbackend.exceptions.*;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.EPSILON;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.EMPTY_SET;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.OPEN_BRACKET;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.CLOSED_BRACKET;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.UNION;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.STAR;

public class RegexValidator {
 
    private enum PreviousChar{
        NONE,
        VALIDCHAR,
        OPEN_BRACKET,
        CLOSED_BRACKET,
        UNION,
        STAR
    }

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
