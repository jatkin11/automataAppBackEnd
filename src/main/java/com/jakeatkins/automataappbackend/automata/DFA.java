package com.jakeatkins.automataappbackend.automata;

import java.util.Map;
import java.util.Set;

public record DFA (Integer startState, 
        Set<Integer> states, 
        Set<Integer> acceptingStates, 
        Set<Character> alphabet,
        Map<Integer, Map<Character,Set<Integer>>> transitionMap,
        Map<Integer,String> stateLabelMap) implements Automata {}
    
