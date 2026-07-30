package com.jakeatkins.automataappbackend.algos;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.dfaSameStartAndAccepting;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.dfaSingleA;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.dfaWithMultipleAcceptingStates;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaOfDfaSameStartAndAcceptingReversed;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaOfdfaSingleAReversed;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaOfdfaWithMultipleAcceptingStatesReversed;

public class DfaReverserTester {
    
    @Test
    void newStartStateCreated(){
        assertEquals(2, DfaReverser.reverse(dfaSingleA()).startState());
    }

    @Test
    void newStartStateDoesCollideWithExistingStates(){
        assertFalse(dfaSingleA().states().contains(DfaReverser.reverse(dfaSingleA()).startState()));
    }

    @Test
    void originalStartStateBecomesNewAcceptingState(){
        assertEquals(Set.of(dfaSingleA().startState()),DfaReverser.reverse(dfaSingleA()).acceptingStates());
    }

    @Test
    void addsEpsilonTransitionsFromNewStartStateToEveryOriginalAcceptingState(){
        
    }

    @Test
    void reversesAllTransitions(){
        assertEquals(nfaOfdfaSingleAReversed().transitionMap(), DfaReverser.reverse(dfaSingleA()).transitionMap());
    }

    @Test
    void reversesDfaWithSingleAcceptingState(){
        assertEquals(nfaOfdfaSingleAReversed(),DfaReverser.reverse(dfaSingleA()));
    }

    @Test
    void reversesDfaWithMultipleAcceptingStates(){
        assertEquals(nfaOfdfaWithMultipleAcceptingStatesReversed(),DfaReverser.reverse(dfaWithMultipleAcceptingStates()));
    }

    @Test
    void returnsAnNFA(){
        assertEquals(nfaOfdfaSingleAReversed(),DfaReverser.reverse(dfaSingleA()));
    }

    @Test
    void reversesDFAWithSameStartAndAcceptingState(){
        assertEquals(nfaOfDfaSameStartAndAcceptingReversed(),DfaReverser.reverse(dfaSameStartAndAccepting()));
    }


}
