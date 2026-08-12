package com.jakeatkins.automataappbackend.algos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.jakeatkins.automataappbackend.regex.RegexConcat;
import com.jakeatkins.automataappbackend.regex.RegexEmptySet;
import com.jakeatkins.automataappbackend.regex.RegexStarred;
import com.jakeatkins.automataappbackend.regex.RegexSymbol;
import com.jakeatkins.automataappbackend.regex.RegexUnion;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaEmptyWord;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaSingleA;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaSingleAWithNoAcceptingState;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithConcatAB;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithMultipleBranches;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithSelfLoop;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithTwoEpsilonJumpThenA;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithUnionAorB;

public class AutomataToRegexConverterTester {
    

    @Test
    void convertsSingleSymbolNFA(){
        assertEquals(new RegexSymbol('a',-1), new AutomataToRegexConverter(nfaSingleA()).convert());
    }

    @Test
    void convertsConcatNFA(){
        assertEquals(new RegexConcat(new RegexSymbol('a', -1), new RegexSymbol('b',-1)), new AutomataToRegexConverter(nfaWithConcatAB()).convert());
    }

     @Test
    void convertsUnionNFA(){
        assertEquals(new RegexUnion(new RegexSymbol('a', -1), new RegexSymbol('b',-1)), new AutomataToRegexConverter(nfaWithUnionAorB()).convert());
    }

    @Test
     void convertsStarredContainingNFA(){
        assertEquals(new RegexStarred(new RegexSymbol('a',-1)),new AutomataToRegexConverter(nfaWithSelfLoop()).convert());
    }

     @Test
    void convertsNFAofEmptyWord(){
        assertEquals(new RegexEmptySet(), new AutomataToRegexConverter(nfaEmptyWord()).convert());
    }

     @Test
    void convertsNFAWithNoAcceptingStates(){
        assertEquals(new RegexEmptySet(), new AutomataToRegexConverter(nfaSingleAWithNoAcceptingState()).convert());
    }

     @Test
    void convertsDFAWithMultipleAcceptingStates(){
        assertEquals(new RegexSymbol('a',-1), new AutomataToRegexConverter(nfaWithMultipleBranches()).convert());
    }

    @Test
    void convertsNFAWithEpsilons(){
        assertEquals(new RegexSymbol('a',-1), new AutomataToRegexConverter(nfaWithTwoEpsilonJumpThenA()).convert());
    }

 

}
