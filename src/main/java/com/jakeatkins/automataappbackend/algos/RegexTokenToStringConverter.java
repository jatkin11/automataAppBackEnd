package com.jakeatkins.automataappbackend.algos;

import com.jakeatkins.automataappbackend.regex.RegexConcat;
import com.jakeatkins.automataappbackend.regex.RegexEmptySet;
import com.jakeatkins.automataappbackend.regex.RegexEpsilon;
import com.jakeatkins.automataappbackend.regex.RegexStarred;
import com.jakeatkins.automataappbackend.regex.RegexSymbol;
import com.jakeatkins.automataappbackend.regex.RegexToken;
import com.jakeatkins.automataappbackend.regex.RegexUnion;

public class RegexTokenToStringConverter {
    
    private static final char EPSILON = 'ε';
    private static final char EMPTY_SET = '∅';

    public static String convert(RegexToken regex){

        return switch (regex) {
            case RegexSymbol r-> String.valueOf(r.getSymbol());
            case RegexEpsilon r -> String.valueOf(EPSILON);
            case RegexEmptySet r-> String.valueOf(EMPTY_SET);
            case RegexStarred r-> "(" + convert(r.getStarredRegex()) + ")*";
            case RegexConcat r-> "(" + convert(r.getLeft()) + convert(r.getRight()) + ")";
            case RegexUnion r-> "(" + convert(r.getLeft()) + "|" + convert(r.getRight()) + ")";
            default -> throw new IllegalArgumentException("Invalid regex");};
    }
}




