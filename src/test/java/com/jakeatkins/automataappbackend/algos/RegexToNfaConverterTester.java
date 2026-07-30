package com.jakeatkins.automataappbackend.algos;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaComplex;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaEmptyWord;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaSingleA;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithConcatAB;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithEpsilonOnly;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithStarredA;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithUnionAorB;

public class RegexToNfaConverterTester {
    
    @Test
    void convertsSingleA(){
        assertEquals(nfaSingleA(), new RegexToNfaConverter(new RegexTokeniser("a").tokenise()).convert());
    }

    @Test
    void convertsConcatnAB(){
        assertEquals(nfaWithConcatAB(), new RegexToNfaConverter(new RegexTokeniser("ab").tokenise()).convert());
    }

    @Test
    void convertsUnionAB(){
        assertEquals(nfaWithUnionAorB(), new RegexToNfaConverter(new RegexTokeniser("(a|b)").tokenise()).convert());  
    }

    @Test
    void convertsAStarred(){
        assertEquals(nfaWithStarredA(), new RegexToNfaConverter(new RegexTokeniser("a*").tokenise()).convert());
    }

    @Test
    void convertsComplexRegex(){
                assertEquals(nfaComplex(), new RegexToNfaConverter(new RegexTokeniser("(a|b)*abb").tokenise()).convert());
    }

    @Test
    void convertsEpsilon(){
        assertEquals(nfaWithEpsilonOnly(), new RegexToNfaConverter(new RegexTokeniser("ε").tokenise()).convert());
    }

    @Test
    void convertsEmptySet(){
        assertEquals(nfaEmptyWord(), new RegexToNfaConverter(new RegexTokeniser("∅").tokenise()).convert());
    }

    @Test
    void correctAlphabet(){
            assertEquals(Set.of('a','b','c','d','e','f'), new RegexToNfaConverter(new RegexTokeniser("abcdef").tokenise()).convert().alphabet());
    }

    @Test
    void correctStartState(){
        assertEquals(0, new RegexToNfaConverter(new RegexTokeniser("ab").tokenise()).convert().startState());
    }

    @Test
    void correctAcceptingStates(){
        assertEquals(Set.of(1,2), new RegexToNfaConverter(new RegexTokeniser("a|b").tokenise()).convert().acceptingStates());
    }

    @Test
    void correctStateLabels(){
        assertEquals(Map.of(0,"q0",1,"q1",2,"q2"), new RegexToNfaConverter(new RegexTokeniser("ab").tokenise()).convert().stateLabelMap());
    }

    @Test
    void correctEdgeSourcesAndTargets(){
        assertEquals(Map.of(0,Map.of('a',Set.of(1)),1,Map.of('b',Set.of(2))), new RegexToNfaConverter(new RegexTokeniser("ab").tokenise()).convert().transitionMap());
    }

    @Test
    void correctStateIds(){
        assertEquals(Set.of(0,1,2), new RegexToNfaConverter(new RegexTokeniser("ab").tokenise()).convert().states());
    }

}
