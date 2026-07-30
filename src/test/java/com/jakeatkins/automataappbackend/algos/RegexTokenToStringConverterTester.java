package com.jakeatkins.automataappbackend.algos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.jakeatkins.automataappbackend.regex.RegexToken;
import static com.jakeatkins.automataappbackend.utilities.TestRegexTokenCreator.RegexTokenOfConcatAandB;
import static com.jakeatkins.automataappbackend.utilities.TestRegexTokenCreator.RegexTokenOfEmptySet;
import static com.jakeatkins.automataappbackend.utilities.TestRegexTokenCreator.RegexTokenOfGroupedAConcatWithConcatBC;
import static com.jakeatkins.automataappbackend.utilities.TestRegexTokenCreator.RegexTokenOfNestedAConcatBSurroundedByStar;
import static com.jakeatkins.automataappbackend.utilities.TestRegexTokenCreator.RegexTokenOfSingleEpsilon;
import static com.jakeatkins.automataappbackend.utilities.TestRegexTokenCreator.RegexTokenOfSingleSymbolA;
import static com.jakeatkins.automataappbackend.utilities.TestRegexTokenCreator.RegexTokenOfStarredA;
import static com.jakeatkins.automataappbackend.utilities.TestRegexTokenCreator.RegexTokenOfUnionOfAunionB;


public class RegexTokenToStringConverterTester {
    
    @Test
    void convertsSingleACorrectly(){
        assertEquals("a", RegexTokenToStringConverter.convert(RegexTokenOfSingleSymbolA()));
    }

    @Test
    void convertsConcatCorrectly(){
        assertEquals("(ab)", RegexTokenToStringConverter.convert(RegexTokenOfConcatAandB()));
    }

    @Test
    void convertsUnionCorrectly(){
        assertEquals("(a|b)", RegexTokenToStringConverter.convert(RegexTokenOfUnionOfAunionB()));
    }

    @Test
    void convertsStarredCorrectly(){
        assertEquals("(a)*", RegexTokenToStringConverter.convert(RegexTokenOfStarredA()));
    }

    @Test
    void convertsEpsilonCorrectly(){
        assertEquals("ε", RegexTokenToStringConverter.convert(RegexTokenOfSingleEpsilon()));
    }

    @Test
    void convertsEmptySetCorrectly(){
                assertEquals("∅", RegexTokenToStringConverter.convert(RegexTokenOfEmptySet()));
    }

    @Test
    void correctlyBrackets(){
        assertEquals("(a(bc))", RegexTokenToStringConverter.convert(RegexTokenOfGroupedAConcatWithConcatBC()));
    }

    @Test
    void convertsNestedCorrectly(){
        assertEquals("((ab))*", RegexTokenToStringConverter.convert(RegexTokenOfNestedAConcatBSurroundedByStar()));
    }

    @Test
    void correctlyConvertsBackIntoSameRegexToken(){
        String test =  RegexTokenToStringConverter.convert(RegexTokenOfGroupedAConcatWithConcatBC());
        RegexToken testToken = new RegexTokeniser(test).tokenise();
        assertEquals(testToken, RegexTokenOfGroupedAConcatWithConcatBC());
    }


}
