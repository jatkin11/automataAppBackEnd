package com.jakeatkins.automataappbackend.validators;

import java.util.ArrayDeque;
import java.util.Deque;

public class RegexStringValidator {
 
    private static final char EPSILON = 'ε';
    private static final char EMPTY_SET = '∅';
    private static final char UNION = '|';
    private static final char STAR = '*';
    private static final char OPEN_BRACKET = '(';
    private static final char CLOSED_BRACKET = ')';

    private enum PreviousChar{
        NONE,
        VALIDCHAR,
        OPEN_BRACKET,
        CLOSED_BRACKET,
        UNION,
        STAR
    }

    public static boolean validateRegexString(String inputRegex){
        if(inputRegex == null){return false;}
        
        String regex = inputRegex.strip();

        if(regex.isEmpty()){ return false;}

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
                    return false;
                    }
                    if(previousChar == PreviousChar.OPEN_BRACKET || previousChar == PreviousChar.UNION){
                        return false;
                    }
                    stack.pop();
                    previousChar = PreviousChar.CLOSED_BRACKET;
                }
                case UNION -> {
                    if(previousChar == PreviousChar.NONE || previousChar == PreviousChar.OPEN_BRACKET || previousChar == PreviousChar.UNION){
                    return false;
                    }
                    previousChar = PreviousChar.UNION;
                }
                case STAR -> {
                    if(previousChar != PreviousChar.VALIDCHAR && previousChar != PreviousChar.CLOSED_BRACKET){
                    return false;
                    }
                    previousChar = PreviousChar.STAR;
                }
                default -> {
                    if(!Character.isLetterOrDigit(c))
                    {return false;}
                    previousChar = PreviousChar.VALIDCHAR;
                }
            }
        }
        if(previousChar == PreviousChar.UNION || previousChar == PreviousChar.OPEN_BRACKET){return false;}
        return stack.isEmpty();
    }           
}
