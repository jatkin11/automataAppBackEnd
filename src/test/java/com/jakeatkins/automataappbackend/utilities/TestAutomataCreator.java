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

    public static NFA nfaWithUnionAorB(){

    }

    public static NFA nfaWithConcatAB(){

    }

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

    public static NFA nfaEmptyWord(){

    }

    public static NFA nfaEndingWithAB(){


    }


    public static NFA nfaWithEpsilonLoop(){


    }



    public static DFA dfaWithEvenNumberOfA(){

    }

}
