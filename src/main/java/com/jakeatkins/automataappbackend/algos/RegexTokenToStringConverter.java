package com.jakeatkins.automataappbackend.algos;

import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.CLOSED_BRACKET;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.EMPTY_SET;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.EPSILON;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.OPEN_BRACKET;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.STAR;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.UNION;
import com.jakeatkins.automataappbackend.exceptions.InvalidRegexException;
import com.jakeatkins.automataappbackend.exceptions.InvalidRegexTokenException;
import com.jakeatkins.automataappbackend.regex.RegexConcat;
import com.jakeatkins.automataappbackend.regex.RegexEmptySet;
import com.jakeatkins.automataappbackend.regex.RegexEpsilon;
import com.jakeatkins.automataappbackend.regex.RegexStarred;
import com.jakeatkins.automataappbackend.regex.RegexSymbol;
import com.jakeatkins.automataappbackend.regex.RegexToken;
import com.jakeatkins.automataappbackend.regex.RegexUnion;

/**
 * 
 * RegexTokenToStringConverter
 * 
 * Converts RegexToken trees into regex strings e.g. RegexUnion(RegexSymbol('a'),RegexSymbol('b')) would produce: '(a|b)'
 * 
 */
public class RegexTokenToStringConverter {
    
    /**
     * Converts RegexToken tree into string form regex
     * 
     * recursively traverses the tree to build regex string
     * 
     * @param regex RegexToken tree
     * @return String of regex
     * @throws InvalidRegexTokenException if RegexToken tree is null
     * @throws InvalidRegexException if regex is invalid
     */
    public static String convert(RegexToken regex){
        if(regex == null){
            throw new InvalidRegexTokenException("RegexToken cannot be null");
        }

        return switch (regex) {
            case RegexSymbol r-> String.valueOf(r.symbol());
            case RegexEpsilon r -> String.valueOf(EPSILON);
            case RegexEmptySet r-> String.valueOf(EMPTY_SET);
            case RegexStarred r-> OPEN_BRACKET + convert(r.starredRegex()) + CLOSED_BRACKET + STAR;
            case RegexConcat r-> OPEN_BRACKET + convert(r.left()) + convert(r.right()) + CLOSED_BRACKET;
            case RegexUnion r-> OPEN_BRACKET + convert(r.left()) + UNION + convert(r.right()) + CLOSED_BRACKET;
            default -> throw new InvalidRegexException("Invalid regexToken tree");};
    }
}




