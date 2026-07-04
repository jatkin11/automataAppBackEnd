package com.jakeatkins.automataappbackend.algos;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
        
        Set<Character> alphabet = new HashSet<>(nfa.getAlphabet());
        alphabet.remove(EPSILON);

        Map<Integer,Map<Character,Set<Integer>>> transitionMap = new HashMap<>();

        Map<Integer,String> stateLabelMap = new HashMap<>();
        Map<Set<Integer>,Integer> multipleStatesToSingleDfaIdMap = new HashMap<>();
        
        Set<Integer> dfaStartingStateSet = EpsilonClosure.epsilonClosure(Set.of(nfa.getStartState()), nfa.getTransitionMap());

        if(!Collections.disjoint(dfaStartingStateSet, nfa.getAcceptingStates())){
            acceptingStates.add(startState);
        }

        multipleStatesToSingleDfaIdMap.put(dfaStartingStateSet,startState); 
        states.add(startState);

        stateLabelMap.put(startState,subsetLabelGenerator(dfaStartingStateSet,nfa.getStateLabelMap())); 

        Deque<Set<Integer>> unmarkedStates = new ArrayDeque<>();

        unmarkedStates.add(dfaStartingStateSet);

        while(!unmarkedStates.isEmpty()){

            Set<Integer> currentSetStates = unmarkedStates.pop();
            Integer currentId = multipleStatesToSingleDfaIdMap.get(currentSetStates);
            
            for(Character symbol: alphabet){
                Set<Integer> tempSet = new HashSet<>();

                for(Integer state : currentSetStates){

                    Set<Integer> targetStates = nfaTransitionLookup(state, symbol, nfa.getTransitionMap());
                    tempSet.addAll(targetStates);
                }
                
                Set<Integer> nextSet = EpsilonClosure.epsilonClosure(tempSet, nfa.getTransitionMap());

                if(!multipleStatesToSingleDfaIdMap.containsKey(nextSet)){
                    Integer nextId = idGen.next();
                    multipleStatesToSingleDfaIdMap.put(nextSet, nextId);
                    states.add(nextId);
                    unmarkedStates.push(nextSet);

                    stateLabelMap.put(nextId,subsetLabelGenerator(nextSet,nfa.getStateLabelMap())); 
                    if(!Collections.disjoint(nextSet, nfa.getAcceptingStates())){
                    acceptingStates.add(nextId);
                    }
                }

                Integer targetId = multipleStatesToSingleDfaIdMap.get(nextSet);

                transitionMap.computeIfAbsent(currentId, r -> new HashMap<>()).computeIfAbsent(symbol,r-> new HashSet<>()).add(targetId);
            }
         }  
        return new DFA(startState,states,acceptingStates,alphabet,transitionMap,stateLabelMap);
    }

    private static Set<Integer> nfaTransitionLookup(int state, char symbol, Map<Integer, Map<Character, Set<Integer>>> nfaTransitionMap){
        if(!nfaTransitionMap.containsKey(state) ||!nfaTransitionMap.get(state).containsKey(symbol)){
            return Collections.emptySet();
        }
        return nfaTransitionMap.get(state).get(symbol);
    }

    private static String subsetLabelGenerator(Set<Integer> subset, Map<Integer,String> nfaTransitionMap){
        if(subset.isEmpty()){return "∅";}

        return subset.stream().sorted()
        .map(r -> {
            String label = nfaTransitionMap.get(r);
            if(label == null ||label.isBlank()){
                return "q" + r;
            }
            return label;
        })
        .collect(Collectors.joining(",","{","}"));
    }
}
