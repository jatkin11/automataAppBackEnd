package com.jakeatkins.automataappbackend.utilities;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.jakeatkins.automataappbackend.automata.DFA;
import com.jakeatkins.automataappbackend.automata.NFA;

public class TestAutomataCreator {
    
    public static NFA nfaSingleA(){
        Map<Integer, Map<Character,Set<Integer>>> transitionMap = new HashMap<>();
        transitionMap.put(0,Map.of('a', Set.of(1)));
        Integer startState = 0;
        Set<Character> alphabet = Set.of('a');
        Set<Integer> acceptingStates = Set.of(1);
        Set<Integer> states = Set.of(0,1);
        Map<Integer,String> stateLabelMap = Map.of(0,"q0",1,"q1");

        return new NFA(startState, states, acceptingStates, alphabet, transitionMap, stateLabelMap);
    }

    public static NFA nfaSingleAWithNoAcceptingState(){
        Map<Integer, Map<Character,Set<Integer>>> transitionMap = new HashMap<>();
        transitionMap.put(0,Map.of('a', Set.of(1)));
        Integer startState = 0;
        Set<Character> alphabet = Set.of('a');
        Set<Integer> acceptingStates = Collections.emptySet();
        Set<Integer> states = Set.of(0,1);
        Map<Integer,String> stateLabelMap = Map.of(0,"q0",1,"q1");

        return new NFA(startState, states, acceptingStates, alphabet, transitionMap, stateLabelMap);
    }

    public static NFA nfaSingleAWithStartStateAsAccepting(){
        Map<Integer, Map<Character,Set<Integer>>> transitionMap = new HashMap<>();
        transitionMap.put(0,Map.of('a', Set.of(1)));
        Integer startState = 0;
        Set<Character> alphabet = Set.of('a');
        Set<Integer> acceptingStates = Set.of(0);
        Set<Integer> states = Set.of(0,1);
        Map<Integer,String> stateLabelMap = Map.of(0,"q0",1,"q1");

        return new NFA(startState, states, acceptingStates, alphabet, transitionMap, stateLabelMap);
    }

    public static DFA dfaSingleA(){
        Map<Integer, Map<Character,Set<Integer>>> transitionMap = new HashMap<>();
        transitionMap.put(0,Map.of('a', Set.of(1)));
        Integer startState = 0;
        Set<Character> alphabet = Set.of('a');
        Set<Integer> acceptingStates = Set.of(1);
        Set<Integer> states = Set.of(0,1);
        Map<Integer,String> stateLabelMap = Map.of(0,"q0",1,"q1");

        return new DFA(startState, states, acceptingStates, alphabet, transitionMap, stateLabelMap);

    }

    public static NFA nfaWithStarredA(){
       Map<Integer, Map<Character,Set<Integer>>> transitionMap = new HashMap<>();
        transitionMap.put(0,Map.of('a', Set.of(0)));
        Integer startState = 0;
        Set<Character> alphabet = Set.of('a');
        Set<Integer> acceptingStates = Set.of(0);
        Set<Integer> states = Set.of(0);
        Map<Integer,String> stateLabelMap = Map.of(0,"q0");

        return new NFA(startState, states, acceptingStates, alphabet, transitionMap, stateLabelMap);
    }

    // public static NFA nfaWithUnionAorB(){

    // }

    // public static NFA nfaWithConcatAB(){

    // }

    public static NFA nfaWithTwoEpsilonJumpThenA(){
       Map<Integer, Map<Character,Set<Integer>>> transitionMap = new HashMap<>();
        transitionMap.put(0,Map.of('ε', Set.of(1)));
        transitionMap.put(  1,Map.of('ε', Set.of(2)));
        transitionMap.put(  2,Map.of('a', Set.of(3)));
        Integer startState = 0;
        Set<Character> alphabet = Set.of('a');
        Set<Integer> acceptingStates = Set.of(3);
        Set<Integer> states = Set.of(0,1,2,3);
        Map<Integer,String> stateLabelMap = Map.of(0,"q0",1,"q1",2,"q2",3,"q3");

        return new NFA(startState, states, acceptingStates, alphabet, transitionMap, stateLabelMap);
    }

    // public static NFA nfaEmptyWord(){

    // }

    // public static NFA nfaEndingWithAB(){


    // }


    // public static NFA nfaWithEpsilonLoop(){


    // }



    // public static DFA dfaWithEvenNumberOfA(){

    // }


    public static NFA nfaWithMultipleBranches(){
       Map<Integer, Map<Character,Set<Integer>>> transitionMap = new HashMap<>();
        transitionMap.put(0,Map.of('a', Set.of(1,2)));
        Integer startState = 0;
        Set<Character> alphabet = Set.of('a');
        Set<Integer> acceptingStates = Set.of(1,2);
        Set<Integer> states = Set.of(0,1,2);
        Map<Integer,String> stateLabelMap = Map.of(0,"q0",1,"q1",2,"q2");

        return new NFA(startState, states, acceptingStates, alphabet, transitionMap, stateLabelMap);
    }


    public static NFA nfaWithNullStates(){
        Map<Integer, Map<Character,Set<Integer>>> transitionMap = new HashMap<>();
        transitionMap.put(0,Map.of('a', Set.of(1)));
        Integer startState = 0;
        Set<Character> alphabet = Set.of('a');
        Set<Integer> acceptingStates = Set.of(1);
        Map<Integer,String> stateLabelMap = Map.of(0,"q0",1,"q1");

        return new NFA(startState, null, acceptingStates, alphabet, transitionMap, stateLabelMap);
    }

        public static NFA nfaWithEmptyStates(){
        Map<Integer, Map<Character,Set<Integer>>> transitionMap = new HashMap<>();
        transitionMap.put(0,Map.of('a', Set.of(1)));
        Integer startState = 0;
        Set<Character> alphabet = Set.of('a');
        Set<Integer> acceptingStates = Set.of(1);
        Map<Integer,String> stateLabelMap = Map.of(0,"q0",1,"q1");

        return new NFA(startState, Collections.emptySet(), acceptingStates, alphabet, transitionMap, stateLabelMap);
    }

    public static NFA nfaWithNullStartState(){
        Map<Integer, Map<Character,Set<Integer>>> transitionMap = new HashMap<>();
        transitionMap.put(0,Map.of('a', Set.of(1)));
        Set<Character> alphabet = Set.of('a');
        Set<Integer> acceptingStates = Set.of(1);
        Set<Integer> states = Set.of(0,1);
        Map<Integer,String> stateLabelMap = Map.of(0,"q0",1,"q1");

        return new NFA(null, states, acceptingStates, alphabet, transitionMap, stateLabelMap);

    }



    public static NFA nfaWithNullAcceptingStates(){
        Map<Integer, Map<Character,Set<Integer>>> transitionMap = new HashMap<>();
        transitionMap.put(0,Map.of('a', Set.of(1)));
        Integer startState = 0;
        Set<Character> alphabet = Set.of('a');
        Set<Integer> states = Set.of(0,1);
        Map<Integer,String> stateLabelMap = Map.of(0,"q0",1,"q1");

        return new NFA(startState, states, null, alphabet, transitionMap, stateLabelMap);

    }



    public static NFA nfaWithNullTransitionMap(){
        Integer startState = 0;
        Set<Character> alphabet = Set.of('a');
        Set<Integer> acceptingStates = Set.of(1);
        Set<Integer> states = Set.of(0,1);
        Map<Integer,String> stateLabelMap = Map.of(0,"q0",1,"q1");

        return new NFA(startState, states, acceptingStates, alphabet, null, stateLabelMap);

    }


    public static NFA nfaWithNullStateLabelMap(){
        Map<Integer, Map<Character,Set<Integer>>> transitionMap = new HashMap<>();
        transitionMap.put(0,Map.of('a', Set.of(1)));
        Integer startState = 0;
        Set<Character> alphabet = Set.of('a');
        Set<Integer> acceptingStates = Set.of(1);
        Set<Integer> states = Set.of(0,1);
        return new NFA(startState, states, acceptingStates, alphabet, transitionMap, null);
    }



    public static NFA nfaWithTransisitionMapContainingNullSourceStates(){
        Map<Integer, Map<Character,Set<Integer>>> transitionMap = new HashMap<>();
        transitionMap.put(null,Map.of('a', Set.of(1)));
        Integer startState = 0;
        Set<Character> alphabet = Set.of('a');
        Set<Integer> acceptingStates = Set.of(1);
        Set<Integer> states = Set.of(0,1);
        Map<Integer,String> stateLabelMap = Map.of(0,"q0",1,"q1");

        return new NFA(startState, states, acceptingStates, alphabet, transitionMap, stateLabelMap);

    }


    public static NFA nfaWithTransisitionMapContainingNullTargetStates(){
        Map<Integer, Map<Character,Set<Integer>>> transitionMap = new HashMap<>();
        transitionMap.put(0,null);
        Integer startState = 0;
        Set<Character> alphabet = Set.of('a');
        Set<Integer> acceptingStates = Set.of(1);
        Set<Integer> states = Set.of(0,1);
        Map<Integer,String> stateLabelMap = Map.of(0,"q0",1,"q1");

        return new NFA(startState, states, acceptingStates, alphabet, transitionMap, stateLabelMap);

    }


    public static NFA nfaWithTransisitionMapContainingStatesNotInStatesSet(){
        Map<Integer, Map<Character,Set<Integer>>> transitionMap = new HashMap<>();
        transitionMap.put(0,Map.of('a', Set.of(1)));
        transitionMap.put(1,Map.of('b', Set.of(2)));
        Integer startState = 0;
        Set<Character> alphabet = Set.of('a');
        Set<Integer> acceptingStates = Set.of(1);
        Set<Integer> states = Set.of(0,1);
        Map<Integer,String> stateLabelMap = Map.of(0,"q0",1,"q1");

        return new NFA(startState, states, acceptingStates, alphabet, transitionMap, stateLabelMap);

    }

    public static NFA nfaWithNullAlphabet(){
        Map<Integer, Map<Character,Set<Integer>>> transitionMap = new HashMap<>();
        transitionMap.put(0,Map.of('a', Set.of(1)));
        Integer startState = 0;
        Set<Integer> acceptingStates = Set.of(1);
        Set<Integer> states = Set.of(0,1);
        Map<Integer,String> stateLabelMap = Map.of(0,"q0",1,"q1");

        return new NFA(startState, states, acceptingStates, null, transitionMap, stateLabelMap);

    }

    public static NFA nfaWithInvalidAlphabet(){
        Map<Integer, Map<Character,Set<Integer>>> transitionMap = new HashMap<>();
        transitionMap.put(0,Map.of('&', Set.of(1)));
        Integer startState = 0;
        Set<Character> alphabet = Set.of('&','^','$');
        Set<Integer> acceptingStates = Set.of(1);
        Set<Integer> states = Set.of(0,1);
        Map<Integer,String> stateLabelMap = Map.of(0,"q0",1,"q1");

        return new NFA(startState, states, acceptingStates, alphabet, transitionMap, stateLabelMap);

    }

    public static NFA nfaWithLettersOnlyInAlphabet(){
        Map<Integer, Map<Character,Set<Integer>>> transitionMap = new HashMap<>();
        transitionMap.put(0,Map.of('a', Set.of(1)));
        Integer startState = 0;
        Set<Character> alphabet = Set.of('a','b','C');
        Set<Integer> acceptingStates = Set.of(1);
        Set<Integer> states = Set.of(0,1);
        Map<Integer,String> stateLabelMap = Map.of(0,"q0",1,"q1");

        return new NFA(startState, states, acceptingStates, alphabet, transitionMap, stateLabelMap);

    }

    public static NFA nfaWithDigitsOnlyInAlphabet(){
        Map<Integer, Map<Character,Set<Integer>>> transitionMap = new HashMap<>();
        transitionMap.put(0,Map.of('1', Set.of(1)));
        Integer startState = 0;
        Set<Character> alphabet = Set.of('1','2','3');
        Set<Integer> acceptingStates = Set.of(1);
        Set<Integer> states = Set.of(0,1);
        Map<Integer,String> stateLabelMap = Map.of(0,"q0",1,"q1");

        return new NFA(startState, states, acceptingStates, alphabet, transitionMap, stateLabelMap);

    }

    public static NFA nfaWithEpsilonOnlyInAlphabet(){
        Map<Integer, Map<Character,Set<Integer>>> transitionMap = new HashMap<>();
        transitionMap.put(0,Map.of('ε', Set.of(1)));
        Integer startState = 0;
        Set<Character> alphabet = Set.of('ε');
        Set<Integer> acceptingStates = Set.of(1);
        Set<Integer> states = Set.of(0,1);
        Map<Integer,String> stateLabelMap = Map.of(0,"q0",1,"q1");

        return new NFA(startState, states, acceptingStates, alphabet, transitionMap, stateLabelMap);

    }

}
