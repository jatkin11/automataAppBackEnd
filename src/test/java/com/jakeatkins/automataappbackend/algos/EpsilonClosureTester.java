package com.jakeatkins.automataappbackend.algos;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaSingleA;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithBranchingEpsilonJumps;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithConcatAB;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithEpsilonJumpCycle;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithEpsilonOnly;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithEpsilonOnlyInAlphabet;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithTwoEpsilonJumpThenA;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithUnionAorB;

public class EpsilonClosureTester {
    
    @Test
    void closureIncludesOriginalStates(){
        assertTrue(EpsilonClosure.epsilonClosure(Set.of(nfaSingleA().startState()), nfaSingleA().transitionMap()).contains(nfaSingleA().startState()));
        assertTrue(EpsilonClosure.epsilonClosure(Set.of(nfaWithTwoEpsilonJumpThenA().startState()), nfaWithTwoEpsilonJumpThenA().transitionMap()).contains(nfaWithTwoEpsilonJumpThenA().startState()));
    }

    @Test
    void closureFollowsSingularEpsilonJump(){
      assertEquals(Set.of(0,1),EpsilonClosure.epsilonClosure(Set.of(nfaWithEpsilonOnlyInAlphabet().startState()), nfaWithEpsilonOnlyInAlphabet().transitionMap()));
    }

    @Test
    void closureFollowsMultipleEpsilonJumps(){
      assertEquals(Set.of(0,1,2),EpsilonClosure.epsilonClosure(Set.of(nfaWithTwoEpsilonJumpThenA().startState()), nfaWithTwoEpsilonJumpThenA().transitionMap()));
    }

    @Test
    void closureFollowsBranchingEpsilonJumps(){
      assertEquals(Set.of(0,1,2),EpsilonClosure.epsilonClosure(Set.of(nfaWithBranchingEpsilonJumps().startState()), nfaWithBranchingEpsilonJumps().transitionMap()));
    }


        @Test
    void closureFollowsEpsilonCycle(){
      assertEquals(Set.of(0,1),EpsilonClosure.epsilonClosure(Set.of(nfaWithEpsilonJumpCycle().startState()), nfaWithEpsilonJumpCycle().transitionMap()));
    }

    @Test
    void closureSkipsNonEpislonJumps(){
        assertEquals(Set.of(0),EpsilonClosure.epsilonClosure(Set.of(nfaWithConcatAB().startState()), nfaWithConcatAB().transitionMap()));
        assertEquals(Set.of(0),EpsilonClosure.epsilonClosure(Set.of(nfaWithUnionAorB().startState()), nfaWithUnionAorB().transitionMap()));
    }

    @Test
    void emptySetClosureHasEmptyClosure(){
      assertEquals(Set.of(0),EpsilonClosure.epsilonClosure(Set.of(nfaWithEpsilonOnly().startState()), nfaWithEpsilonOnly().transitionMap()));
    }

}
