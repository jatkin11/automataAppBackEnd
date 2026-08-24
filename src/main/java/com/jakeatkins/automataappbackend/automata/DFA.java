package com.jakeatkins.automataappbackend.automata;

import java.util.Map;
import java.util.Set;

/**
 * 
 * DFA
 * 
 * Represents the Back-end DFA (deterministic finite automaton)
 * 
 * Implements Automata Interface
 * 
 * @param startState the automatons starting state
 * @param states the automatons set of states
 * @param acceptingStates the automatons set of accepting states
 * @param alphabet the automatons alphabet (set of char)
 * @param transitionMap the transition map between source and target states within an automaton e.g. 0 - 'a' -> 1
 * @param stateLabelMap a map of state IDs vs their label to be displayed in the front-end e.g. <0, "q0">
 */
public record DFA (Integer startState, 
        Set<Integer> states, 
        Set<Integer> acceptingStates, 
        Set<Character> alphabet,
        Map<Integer, Map<Character,Set<Integer>>> transitionMap,
        Map<Integer,String> stateLabelMap) implements Automata {}
    
