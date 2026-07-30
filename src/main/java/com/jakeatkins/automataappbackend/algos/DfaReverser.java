package com.jakeatkins.automataappbackend.algos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.EPSILON;
import com.jakeatkins.automataappbackend.automata.DFA;
import com.jakeatkins.automataappbackend.automata.NFA;
import com.jakeatkins.automataappbackend.validators.AutomataValidator;

public class DfaReverser {

    public static NFA reverse(DFA dfa){
        AutomataValidator.validate(dfa);
        Map<Integer,Map<Character,Set<Integer>>> newTransitionMap = new HashMap<>();
        Set<Integer> newStates = new HashSet<>(dfa.states());
        Integer NEW_START_STATE = UniqueStateGenerator.generate(newStates);
        newStates.add(NEW_START_STATE);
        Set<Integer> newAcceptingStates = Set.of(dfa.startState());
        
        for(Integer oldFromState : dfa.transitionMap().keySet()){

            for(Map.Entry<Character,Set<Integer>> entries : dfa.transitionMap().get(oldFromState).entrySet()){

                char symbol = entries.getKey();
                
                for(Integer newFromState : entries.getValue()){
                    newTransitionMap.computeIfAbsent(newFromState, r-> new HashMap<>())
                    .computeIfAbsent(symbol, r-> new HashSet<>()).add(oldFromState);
                }
           }
        }

        for(Integer dfaAcceptingState : dfa.acceptingStates()){
            newTransitionMap.computeIfAbsent(NEW_START_STATE, r-> new HashMap<>())
            .computeIfAbsent(EPSILON, r-> new HashSet<>()).add(dfaAcceptingState);
        }

        Map<Integer,String> newStateLabelMap = new HashMap<>(dfa.stateLabelMap());
        newStateLabelMap.put(NEW_START_STATE, "q" + NEW_START_STATE);

        Set<Character> newAlphabet = new HashSet<>(dfa.alphabet());

        return new NFA(NEW_START_STATE, newStates, newAcceptingStates, newAlphabet,newTransitionMap,newStateLabelMap);
    }
}