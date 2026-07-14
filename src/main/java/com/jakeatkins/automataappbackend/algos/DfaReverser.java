package com.jakeatkins.automataappbackend.algos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.jakeatkins.automataappbackend.automata.DFA;
import com.jakeatkins.automataappbackend.automata.NFA;

public class DfaReverser {

    public static final char EPSILON = 'ε';

    public static NFA reverse(DFA dfa){
        Map<Integer,Map<Character,Set<Integer>>> newTransitionMap = new HashMap<>();
        Set<Integer> newStates = new HashSet<>(dfa.getStates());
        Integer NEW_START_STATE = dfa.getStates().size(); //NEED TO FIX THIS ISSUE, NOT A SAFE WAY OF DOING IT
        newStates.add(NEW_START_STATE);
        Set<Integer> newAcceptingStates = Set.of(dfa.getStartState());
        
        for(Integer oldFromState : dfa.getTransitionMap().keySet()){

            for(Map.Entry<Character,Set<Integer>> entries : dfa.getTransitionMap().get(oldFromState).entrySet()){

                char symbol = entries.getKey();
                
                for(Integer newFromState : entries.getValue()){
                    newTransitionMap.computeIfAbsent(newFromState, r-> new HashMap<>())
                    .computeIfAbsent(symbol, r-> new HashSet<>()).add(oldFromState);
                }
           }
        }

        for(Integer dfaAcceptingState : dfa.getAcceptingStates()){
            newTransitionMap.computeIfAbsent(NEW_START_STATE, r-> new HashMap<>())
            .computeIfAbsent(EPSILON, r-> new HashSet<>()).add(dfaAcceptingState);
        }

        Map<Integer,String> newStateLabelMap = new HashMap<>();

        for(Integer state : newStates){
            newStateLabelMap.put(state, "q" + state); //NEED TO IMPROVE THIS
        }

        return new NFA(NEW_START_STATE, newStates, newAcceptingStates, dfa.getAlphabet(),newTransitionMap,newStateLabelMap);
    }
}