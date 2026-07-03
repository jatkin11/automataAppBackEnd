package com.jakeatkins.automataappbackend.algos;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.jakeatkins.automataappbackend.automata.DFA;
import com.jakeatkins.automataappbackend.automata.NFA;


public class NfaToDfaConverter {
    
    private static final char EPSILON = 'ε';

    public static DFA convert(NFA nfa){

        GlobalStateIdGenerator idGen = new GlobalStateIdGenerator();

        return subsetConstruction(nfa, idGen);
    }

    private static DFA subsetConstruction(NFA nfa, GlobalStateIdGenerator idGen){
        Integer startState = idGen.next(); //internal state should be 0, but extenernal should be epsilon closure of NFA states
        Set<Integer> states = new HashSet<>();
        Set<Integer> acceptingStates = new HashSet<>();
        Set<Character> alphabet = new HashSet<>();
        Map<Integer,Map<Character,Set<Integer>>> transitionMap = new HashMap<>();
        Map<Integer,String> stateLabelMap = new HashMap<>();
        
        Set<Integer> dfaStartingStateSet = EpsilonClosure.epsilonClosure(Set.of(nfa.getStartState()), nfa.getTransitionMap());



        
        
        
        
        
        
        
        
        return new DFA(startState,states,acceptingStates,alphabet,transitionMap,stateLabelMap);
    }


    public static Set<Integer> nfaTransitionLookup(int state, char symbol, Map<Integer, Map<Character, Set<Integer>>> nfaTransitionMap){
        if(!nfaTransitionMap.containsKey(state) ||!nfaTransitionMap.get(state).containsKey(symbol)){
            return Collections.emptySet();
        }

        return nfaTransitionMap.get(state).get(symbol);
    }



}
