package com.jakeatkins.automataappbackend.validators;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.jakeatkins.automataappbackend.automata.DFA;
import com.jakeatkins.automataappbackend.automata.NFA;
import com.jakeatkins.automataappbackend.exceptions.InvalidAutomataException;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.dfaSingleA;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaSingleA;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithDigitsOnlyInAlphabet;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithEmptyStates;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithEpsilonOnlyInAlphabet;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithInvalidAlphabet;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithLettersOnlyInAlphabet;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithMultipleBranches;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithNullAcceptingStates;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithNullAlphabet;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithNullStartState;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithNullStateLabelMap;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithNullStates;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithNullTransitionMap;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithStarredA;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithTransisitionMapContainingNullSourceStates;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithTransisitionMapContainingNullTargetStates;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithTransisitionMapContainingStatesNotInStatesSet;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithTwoEpsilonJumpThenA;

public class AutomataValidatorTester {
    

    @Test
    void acceptsValidNfa(){
        NFA nfa = nfaSingleA();
        assertDoesNotThrow(()->AutomataValidator.validate(nfa));
    }

    @Test
    void acceptsValidDfa(){
        DFA dfa = dfaSingleA();
        assertDoesNotThrow(()->AutomataValidator.validate(dfa));
    }


    @Test
    void acceptsNfaWithEpsilon(){
        NFA nfa = nfaWithTwoEpsilonJumpThenA();
        assertDoesNotThrow(()->AutomataValidator.validate(nfa));
    }


    @Test
    void acceptsSingleStateAutomata(){
        NFA nfa = nfaWithStarredA();
        assertDoesNotThrow(()->AutomataValidator.validate(nfa));
    }


    @Test
    void acceptsStartingStateAlsoAcceptingStateNfa(){
        NFA nfa = nfaWithStarredA();
        assertDoesNotThrow(()->AutomataValidator.validate(nfa));
    }



    @Test
    void acceptsMultipleBranchNfa(){
        NFA nfa = nfaWithMultipleBranches();
        assertDoesNotThrow(()->AutomataValidator.validate(nfa));
    }


    @Test
    void rejectsNullAutomata(){
        assertThrows(InvalidAutomataException.class, ()->AutomataValidator.validate(null));
    }


     @Test
    void rejectsNullStates(){
        NFA nfa = nfaWithNullStates();
        assertThrows(InvalidAutomataException.class,()-> AutomataValidator.validate(nfa));
    }



    @Test
    void rejectsEmptyStates(){
        NFA nfa = nfaWithEmptyStates();
        assertThrows(InvalidAutomataException.class,()-> AutomataValidator.validate(nfa));
    }



    @Test
    void rejectsNullAlphabet(){
        NFA nfa = nfaWithNullAlphabet();
        assertThrows(InvalidAutomataException.class,()-> AutomataValidator.validate(nfa));
    }



    @Test
    void rejectsNullAcceptingStates(){
        NFA nfa = nfaWithNullAcceptingStates();
        assertThrows(InvalidAutomataException.class,()-> AutomataValidator.validate(nfa));
    }



    @Test
    void rejectsNullStartingStates(){
        NFA nfa = nfaWithNullStartState();
        assertThrows(InvalidAutomataException.class,()-> AutomataValidator.validate(nfa));
    }



    @Test
    void rejectsNullTransitionMap(){
        NFA nfa = nfaWithNullTransitionMap();
        assertThrows(InvalidAutomataException.class,()-> AutomataValidator.validate(nfa));
    }



    @Test
    void rejectsNullStateLabelMap(){
        NFA nfa = nfaWithNullStateLabelMap();
        assertThrows(InvalidAutomataException.class,()-> AutomataValidator.validate(nfa));
    }



    @Test
    void rejectsInvalidTransitionOfNullSource(){
        NFA nfa = nfaWithTransisitionMapContainingNullSourceStates();
        assertThrows(InvalidAutomataException.class,()-> AutomataValidator.validate(nfa));
    }
  



    @Test
    void rejectsInvalidTranstionOfSourceNotInStates(){
        NFA nfa = nfaWithTransisitionMapContainingStatesNotInStatesSet();
        assertThrows(InvalidAutomataException.class,()-> AutomataValidator.validate(nfa));
    }




    @Test
    void rejectsInvalidTransitionOfNullSymbolMap(){
        NFA nfa = nfaWithTransisitionMapContainingStatesNotInStatesSet();
        assertThrows(InvalidAutomataException.class,()-> AutomataValidator.validate(nfa));
    }




    @Test
    void rejectsInvalidTransitionOfNullTransitionMap(){
                NFA nfa = nfaWithNullTransitionMap();
        assertThrows(InvalidAutomataException.class,()-> AutomataValidator.validate(nfa));
    }





    @Test
    void rejectsInvalidTransitionOfNullTarget(){
                NFA nfa = nfaWithTransisitionMapContainingNullTargetStates();
        assertThrows(InvalidAutomataException.class,()-> AutomataValidator.validate(nfa));
    }

    @Test
    void rejectsInvalidAlphabetSymbols(){
        NFA nfa = nfaWithInvalidAlphabet();
        assertThrows(InvalidAutomataException.class,()-> AutomataValidator.validate(nfa));
    }



    @Test
    void acceptsLetterSymbols(){
        NFA nfa = nfaWithLettersOnlyInAlphabet();
        assertDoesNotThrow(()-> AutomataValidator.validate(nfa));
    }

    @Test
    void acceptsDigitSymbols(){
        NFA nfa = nfaWithDigitsOnlyInAlphabet();
        assertDoesNotThrow(()-> AutomataValidator.validate(nfa));
    }

    @Test
    void rejectsEpsilonInAlphabet(){
        NFA nfa = nfaWithEpsilonOnlyInAlphabet();
        assertThrows(InvalidAutomataException.class,()-> AutomataValidator.validate(nfa));
    }

}
