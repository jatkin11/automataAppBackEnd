package com.jakeatkins.automataappbackend.automata;

import java.util.Map;
import java.util.Set;

public class NFA implements Automata{
    
    private final Set<Integer> states;
    private final Set<Integer> acceptingStates;
    private final Set<Character> alphabet;
    private final Integer startState;
    private final Map<Integer, Map<Character,Set<Integer>>> transitionMap;
    private final Map<Integer,String> stateLabelMap;
    public static final char EPSILON = 'ε';

    public NFA(
        Integer startState, 
        Set<Integer> states, 
        Set<Integer> acceptingStates, 
        Set<Character> alphabet,
        Map<Integer, Map<Character,Set<Integer>>> transitionMap,
        Map<Integer,String> stateLabelMap
    ){
        this.startState = startState;
        this.states = states;
        this.acceptingStates = acceptingStates;
        this.alphabet = alphabet;
        this.transitionMap = transitionMap;
        this.stateLabelMap = stateLabelMap;
    }

    @Override
    public Set<Character> getAlphabet() {
        return alphabet;
    }

    @Override
    public Integer getStartState() {
        return startState;
    }

    @Override
    public Set<Integer> getAcceptingStates() {
        return acceptingStates;
    }

    @Override
    public Set<Integer> getStates() {
        return states;
    }

    @Override
    public Map<Integer, Map<Character, Set<Integer>>> getTransitionMap() {
        return transitionMap;
    }

    @Override
    public Map<Integer, String> getStateLabelMap() {
        return stateLabelMap;
    }  

    @Override
    public String getLabel(Integer state){
        return stateLabelMap.get(state);
    }

}
