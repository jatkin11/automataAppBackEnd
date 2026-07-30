package com.jakeatkins.automataappbackend.algos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.jakeatkins.automataappbackend.exceptions.InvalidRegexException;
import static com.jakeatkins.automataappbackend.utilities.TestRegexTokenCreator.RegexTokenConcatOverUnionABunionCD;
import static com.jakeatkins.automataappbackend.utilities.TestRegexTokenCreator.RegexTokenOfConcatAandB;
import static com.jakeatkins.automataappbackend.utilities.TestRegexTokenCreator.RegexTokenOfEmptySet;
import static com.jakeatkins.automataappbackend.utilities.TestRegexTokenCreator.RegexTokenOfGroupedAConcatWithConcatBC;
import static com.jakeatkins.automataappbackend.utilities.TestRegexTokenCreator.RegexTokenOfNestedAConcatBSurroundedByStar;
import static com.jakeatkins.automataappbackend.utilities.TestRegexTokenCreator.RegexTokenOfSingleEpsilon;
import static com.jakeatkins.automataappbackend.utilities.TestRegexTokenCreator.RegexTokenOfSingleSymbolA;
import static com.jakeatkins.automataappbackend.utilities.TestRegexTokenCreator.RegexTokenOfStarredA;
import static com.jakeatkins.automataappbackend.utilities.TestRegexTokenCreator.RegexTokenOfUnionOfAunionB;
import static com.jakeatkins.automataappbackend.utilities.TestRegexTokenCreator.RegexTokenStarOverConcatABSTAR;

public class RegexTokeniserTester {
    
@Test
void tokenisesSingleACorrectly(){
    assertEquals(RegexTokenOfSingleSymbolA(), new RegexTokeniser("A").tokenise());
}

@Test
void tokenisesConcatABCorrectly(){
    assertEquals(RegexTokenOfConcatAandB(), new RegexTokeniser("AB").tokenise());
}

@Test
void tokensisesUnionABCorrectly(){
    assertEquals(RegexTokenOfUnionOfAunionB(), new RegexTokeniser("A|B").tokenise());
}


@Test
void tokenisesStarredACorrectly(){
    assertEquals(RegexTokenOfStarredA(), new RegexTokeniser("A*").tokenise());
}

@Test
void tokenisesGroupedCorrectly(){
    assertEquals(RegexTokenOfGroupedAConcatWithConcatBC(), new RegexTokeniser("A(BC)").tokenise());
}

@Test
void tokenisesNestedCorrectly(){
        assertEquals(RegexTokenOfNestedAConcatBSurroundedByStar(), new RegexTokeniser("(AB)*").tokenise());
}


@Test
void tokenisesEmptySetCorrectly(){
    assertEquals(RegexTokenOfEmptySet(), new RegexTokeniser("∅").tokenise());
}


@Test
void tokensisesEpsilonCorrectly(){
        assertEquals(RegexTokenOfSingleEpsilon(), new RegexTokeniser("ε").tokenise());
}


@Test
void rejectsIncorrectUnion(){
    assertThrows(InvalidRegexException.class,()-> new RegexTokeniser("a|").tokenise());
    assertThrows(InvalidRegexException.class,()-> new RegexTokeniser("|a").tokenise());
    assertThrows(InvalidRegexException.class,()-> new RegexTokeniser("a||a").tokenise());
    assertThrows(InvalidRegexException.class,()-> new RegexTokeniser("|").tokenise());

}

@Test
void rejectsIncorrectBrackets(){
    assertThrows(InvalidRegexException.class,()-> new RegexTokeniser("()").tokenise());
    assertThrows(InvalidRegexException.class,()-> new RegexTokeniser("(ab))").tokenise());
    assertThrows(InvalidRegexException.class,()-> new RegexTokeniser(")(").tokenise());
    assertThrows(InvalidRegexException.class,()-> new RegexTokeniser("()))").tokenise());

}

@Test
void rejectsInvalidChars(){
    assertThrows(InvalidRegexException.class,()-> new RegexTokeniser("%").tokenise());
    assertThrows(InvalidRegexException.class,()-> new RegexTokeniser("-").tokenise());
    assertThrows(InvalidRegexException.class,()-> new RegexTokeniser("a&").tokenise());
    assertThrows(InvalidRegexException.class,()-> new RegexTokeniser("((ab)@)").tokenise());

}

@Test
void correctPrecedenceOfStarOverConcat(){
    assertEquals(RegexTokenStarOverConcatABSTAR(), new RegexTokeniser("AB*").tokenise());
}

@Test
void correctPrecedenceOfConcatOverUnion(){
        assertEquals(RegexTokenConcatOverUnionABunionCD(), new RegexTokeniser("AB|CD").tokenise());
}

//COULD PROBABLY ADD GLUSHKOV POSITION TESTS HERE

}
