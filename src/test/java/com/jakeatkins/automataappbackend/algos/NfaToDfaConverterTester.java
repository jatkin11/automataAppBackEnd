package com.jakeatkins.automataappbackend.algos;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.dfaVersionOfNfaSingleA;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.dfaVersionOfNfaWithBranchingEpsilonJumps;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.dfaVersionOfNfaWithTwoEpsilonJumpsThenA;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaComplex;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaSingleA;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaSingleAWithIsolatedStateToBeIgnored;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithBranchingEpsilonJumps;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithEpsilonOnly;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithTwoEpsilonJumpThenA;

public class NfaToDfaConverterTester {
    
    @Test
    void convertsNFAtoDFA(){
        assertEquals(dfaVersionOfNfaSingleA(), NfaToDfaConverter.convert(nfaSingleA()));
    }

   @Test
    void convertsNFAwithEpsilonJumps(){
        assertEquals(dfaVersionOfNfaWithBranchingEpsilonJumps(), NfaToDfaConverter.convert(nfaWithBranchingEpsilonJumps()));
        assertEquals(dfaVersionOfNfaWithTwoEpsilonJumpsThenA(), NfaToDfaConverter.convert(nfaWithTwoEpsilonJumpThenA()));
    }

   @Test
    void correctlyAppliesAcceptingStatesToNewDFA(){
        assertEquals(Set.of(0), NfaToDfaConverter.convert(nfaWithBranchingEpsilonJumps()).acceptingStates());
        assertEquals(Set.of(1), NfaToDfaConverter.convert(nfaWithTwoEpsilonJumpThenA()).acceptingStates());
    }

   @Test
    void correctlyCreatesEmptySetStateForCompleteDFA(){
        assertEquals(Set.of(0), NfaToDfaConverter.convert(nfaWithEpsilonOnly()).states());
    }

   @Test
    void correctlyAppliesAlphabet(){
        assertEquals(Set.of(), NfaToDfaConverter.convert(nfaWithBranchingEpsilonJumps()).alphabet());
        assertEquals(Set.of('a'), NfaToDfaConverter.convert(nfaWithTwoEpsilonJumpThenA()).alphabet());
    }

   @Test
    void newDfaDoesntContainEpsilonInAlphabetInNfaWithEpsilon(){
        assertFalse(NfaToDfaConverter.convert(nfaWithTwoEpsilonJumpThenA()).alphabet().contains('ε'));
    }

   @Test
    void newDfaDoesntContainEpsilonInTransitionMapInNfaWithEpsilon(){
        assertFalse(NfaToDfaConverter.convert(nfaWithTwoEpsilonJumpThenA()).transitionMap().values().stream().anyMatch(r->r.containsKey('ε')));
    }

   @Test
    void newDfaIsCompleteWithAllSymbolsLeavingAllStates(){
        assertEquals(dfaVersionOfNfaSingleA(), NfaToDfaConverter.convert(nfaSingleA()));
    }

   @Test
    void dfaAndNfaAcceptSameWords(){
        assertEquals(WordTester.testAutomata(nfaSingleA(), "a"),WordTester.testAutomata(NfaToDfaConverter.convert(nfaSingleA()), "a"));
        assertEquals(WordTester.testAutomata(nfaSingleA(), "ab"),WordTester.testAutomata(NfaToDfaConverter.convert(nfaSingleA()), "ab"));
        assertEquals(WordTester.testAutomata(nfaWithBranchingEpsilonJumps(), ""),WordTester.testAutomata(NfaToDfaConverter.convert(nfaWithBranchingEpsilonJumps()), ""));
        assertEquals(WordTester.testAutomata(nfaWithBranchingEpsilonJumps(), "aa"),WordTester.testAutomata(NfaToDfaConverter.convert(nfaWithBranchingEpsilonJumps()), "aa"));
        assertEquals(WordTester.testAutomata(nfaComplex(), "abb"),WordTester.testAutomata(NfaToDfaConverter.convert(nfaComplex()), "abb"));
        assertEquals(WordTester.testAutomata(nfaComplex(), "ab"),WordTester.testAutomata(NfaToDfaConverter.convert(nfaComplex()), "ab"));
        assertEquals(WordTester.testAutomata(nfaWithTwoEpsilonJumpThenA(), "a"),WordTester.testAutomata(NfaToDfaConverter.convert(nfaWithTwoEpsilonJumpThenA()), "a"));
        assertEquals(WordTester.testAutomata(nfaWithTwoEpsilonJumpThenA(), "aa"),WordTester.testAutomata(NfaToDfaConverter.convert(nfaWithTwoEpsilonJumpThenA()), "aa"));
    }

   @Test
    void newDfaHasLabelsTakenFromNfaInStateLabelMap(){
        assertEquals(dfaVersionOfNfaSingleA().stateLabelMap(), NfaToDfaConverter.convert(nfaSingleA()).stateLabelMap());
    }

   @Test
    void newDfaIgnoresIsolatedStatesInNfa(){
        assertEquals(dfaVersionOfNfaSingleA(), NfaToDfaConverter.convert(nfaSingleAWithIsolatedStateToBeIgnored()));
    }

}
