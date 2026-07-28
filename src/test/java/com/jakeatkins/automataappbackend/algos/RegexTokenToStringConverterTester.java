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
        assertEquals("A", RegexTokenToStringConverter.convert(RegexTokenOfSingleSymbolA()));
    }

    @Test
    void convertsConcatCorrectly(){
        assertEquals("(AB)", RegexTokenToStringConverter.convert(RegexTokenOfConcatAandB()));
    }

    @Test
    void convertsUnionCorrectly(){
        assertEquals("(A|B)", RegexTokenToStringConverter.convert(RegexTokenOfUnionOfAunionB()));
    }

    @Test
    void convertsStarredCorrectly(){
        assertEquals("(A)*", RegexTokenToStringConverter.convert(RegexTokenOfStarredA()));
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
        assertEquals("(A(BC))", RegexTokenToStringConverter.convert(RegexTokenOfGroupedAConcatWithConcatBC()));
    }

    @Test
    void convertsNestedCorrectly(){
        assertEquals("((AB))*", RegexTokenToStringConverter.convert(RegexTokenOfNestedAConcatBSurroundedByStar()));
    }

    @Test
    void correctlyConvertsBackIntoSameRegexToken(){
        String test =  RegexTokenToStringConverter.convert(RegexTokenOfGroupedAConcatWithConcatBC());
        RegexToken testToken = new RegexTokeniser(test).tokenise();
        assertEquals(testToken, RegexTokenOfGroupedAConcatWithConcatBC());
    }


}
