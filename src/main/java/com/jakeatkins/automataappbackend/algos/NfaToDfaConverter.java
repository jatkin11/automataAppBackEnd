package com.jakeatkins.automataappbackend.algos;

import com.jakeatkins.automataappbackend.automata.*;
import java.util.Map;
import java.util.Set;


public class NfaToDfaConverter {
    
    private static final char EPSILON = 'ε';

    public static DFA convert(NFA nfa){

        GlobalStateIdGenerator idGen = new GlobalStateIdGenerator();

        return subsetConstruction(nfa, idGen);
    }

    private static DFA subsetConstruction(NFA nfa, GlobalStateIdGenerator idGen){
        Integer startState; //internal state should be 0, but extenernal should be epsilon closure of NFA states
        Set<Integer> states;
        Set<Integer> acceptingStates;
        Set<Character> alphabet;
        Map<Integer,Map<Character,Set<Integer>>> transitionMap;
        Map<Integer,String> stateLabelMap;
        
        
        
        
        
        
        
        
        
        
        return new DFA(startState,states,acceptingStates,alphabet,transitionMap,stateLabelMap);
    }






}
