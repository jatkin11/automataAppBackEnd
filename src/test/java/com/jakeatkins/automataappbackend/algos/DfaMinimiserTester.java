package com.jakeatkins.automataappbackend.algos;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.dfaMinimisedOfNfaEpsilonOnly;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.dfaMinimisedOfNfaSingleA;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.dfaMinimisedOfNfaWithUnionAorB;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaComplex;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaSingleA;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithBranchingEpsilonJumps;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithConcatAB;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithEpsilonOnly;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithUnionAorB;
;

public class DfaMinimiserTester {
    

    @Test
    void acceptsValidNFA(){
        assertDoesNotThrow(()-> DfaMinimiser.minimise(nfaSingleA()));
    }

    @Test
    void acceptsNfaWithMultipleAcceptingsStates(){
        assertDoesNotThrow(()-> DfaMinimiser.minimise(nfaWithBranchingEpsilonJumps()));
    }

    @Test
    void correctlyAddsEmptySetStateIfAnNfaIsPassedIn(){
        assertTrue(DfaMinimiser.minimise(nfaSingleA()).stateLabelMap().containsValue("∅"));
    }

    @Test
    void acceptsNfaWithSameStartAndAcceptingState(){
        assertDoesNotThrow(()->DfaMinimiser.minimise(nfaWithEpsilonOnly()));
    }


    @Test
    void returnsACorrectDfa(){
        assertEquals(dfaMinimisedOfNfaSingleA(), DfaMinimiser.minimise(nfaSingleA()));
    }

    @Test
    void returnDoesntContainEpsilonJump(){
        assertEquals(dfaMinimisedOfNfaEpsilonOnly(), DfaMinimiser.minimise(nfaWithEpsilonOnly()));
    }

    @Test
    void returnsADfaWithATransitionForEverySymbolPerSourceState(){
        assertEquals(dfaMinimisedOfNfaWithUnionAorB(), DfaMinimiser.minimise(nfaWithUnionAorB()));
    }

    @Test
    void returnsDfaWithCorrectStartState(){
        assertEquals(dfaMinimisedOfNfaWithUnionAorB(), DfaMinimiser.minimise(nfaWithUnionAorB()));
    }

    @Test
    void inputAutomataAndOutputDfaAcceptSameWords(){
        assertEquals(WordTester.testAutomata(nfaComplex(),"aab"), WordTester.testAutomata(DfaMinimiser.minimise(nfaComplex()), "aab"));
        assertEquals(WordTester.testAutomata(nfaComplex(),"b"), WordTester.testAutomata(DfaMinimiser.minimise(nfaComplex()), "b"));
        assertEquals(WordTester.testAutomata(nfaSingleA(),"aab"), WordTester.testAutomata(DfaMinimiser.minimise(nfaSingleA()), "aab"));
        assertEquals(WordTester.testAutomata(nfaSingleA(),"a"), WordTester.testAutomata(DfaMinimiser.minimise(nfaSingleA()), "a"));
        assertEquals(WordTester.testAutomata(nfaWithConcatAB(),"ab"), WordTester.testAutomata(DfaMinimiser.minimise(nfaWithConcatAB()), "ab"));
        assertEquals(WordTester.testAutomata(nfaWithConcatAB(),"aab"), WordTester.testAutomata(DfaMinimiser.minimise(nfaWithConcatAB()), "aab"));
    }


}
