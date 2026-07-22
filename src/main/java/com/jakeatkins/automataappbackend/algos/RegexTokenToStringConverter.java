package com.jakeatkins.automataappbackend.algos;

import com.jakeatkins.automataappbackend.exceptions.InvalidRegexTokenException;
import com.jakeatkins.automataappbackend.regex.*;
import com.jakeatkins.automataappbackend.exceptions.*;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.EPSILON;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.EMPTY_SET;

public class RegexTokenToStringConverter {
    
    public static String convert(RegexToken regex){
        if(regex == null){
            throw new InvalidRegexTokenException("RegexToken cannot be null");
        }

        return switch (regex) {
            case RegexSymbol r-> String.valueOf(r.symbol());
            case RegexEpsilon r -> String.valueOf(EPSILON);
            case RegexEmptySet r-> String.valueOf(EMPTY_SET);
            case RegexStarred r-> "(" + convert(r.starredRegex()) + ")*";
            case RegexConcat r-> "(" + convert(r.left()) + convert(r.right()) + ")";
            case RegexUnion r-> "(" + convert(r.left()) + "|" + convert(r.right()) + ")";
            default -> throw new InvalidRegexException("Invalid regexToken tree");};
    }
}




