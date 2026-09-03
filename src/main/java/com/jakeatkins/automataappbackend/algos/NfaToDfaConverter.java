package com.jakeatkins.automataappbackend.algos;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.EPSILON;
import com.jakeatkins.automataappbackend.automata.DFA;
import com.jakeatkins.automataappbackend.automata.NFA;
import com.jakeatkins.automataappbackend.validators.AutomataValidator;

/**
 * 
 * NfaToDfaConverter
 * 
 * Converts an NFA to a DFA
 * 
 */
public class NfaToDfaConverter {


    /**
     * Validates the NFA and creates a new instance of the GlobalStateIdGenerator 
     * 
     * Passes the validated NFA and GlobalStateIdGenerator into the subset construction 
     * 
     * @param nfa NFA to convert to DFA
     * @return converted DFA
     */
    public static DFA convert(NFA nfa){
        AutomataValidator.validate(nfa);
        GlobalStateIdGenerator idGen = new GlobalStateIdGenerator();

        return subsetConstruction(nfa, idGen);
    }


    /**
     * The subset construction for converting NFA to DFA
     * 
     * Adapted from the pseudocode found in the report Chapter 4.3 (Norton, 2009)
     * 
     * The below code combines the subset construction, with maintaining the original state labels from the NFA.
     * e.g. If NFA states 1 and 2 are combined during the construction to DFA, the subset label "{q1,q2}" is stored in the DFA state label map with a new generated DFA ID. 
     * The global state generator starts at 0 and for every new DFA state created increments by 1. 
     *  This process uses:
     * - multipleStatesToSingleDfaIdMap: NFA subsets with new DFA Ids
     * - subsetLabelGenerator: gets labels for the NFA subsets
     * - stateLabelMap: stores the new DFA ids with generated display labels of NFA subsets
     *     
     * The subset construction :
     * - Generates new DFA start state ID
     * - copies alphabet from NFA to new DFA alphabet
     * - gets epsilon closure of NFA starting state using EpsilonClosure, as the dfaStartingStateSet
     * - checks if any of the states in the dfaStartingStateSet are NFA accepting state, and if so makes the new start state accepting
     * - adds dfaStartingStateSet to stack
     * - while stack is not empty:
     *      - pop a set of states from the stack,
     *      - for each symbol in the alphabet:
     *          - get the reachable states from the popped set into tempSet
     *          - get the EpsilonClosure of tempSet into the NextSet
     *          - check if NextSet has been visited already
     *              if it isn't:
     *                  - add NextSet to the stack
     *                  - if NextSet contains any accepting states, makes the generated DFA ID an accepting state
     *           - add a transtion to the new DFA transitionMap from the popped set ID, on the current symbol, to the NextSet ID
     * - construct new DFA 
     * 
     * @param nfa NFA to convert
     * @param idGen id generator passed in to create new state IDs for the new DFA
     * @return converted DFA
     */
    private static DFA subsetConstruction(NFA nfa, GlobalStateIdGenerator idGen){
        Integer startState = idGen.next(); 
        Set<Integer> states = new HashSet<>();
        Set<Integer> acceptingStates = new HashSet<>();
        
        Set<Character> alphabet = new HashSet<>(nfa.alphabet());
        alphabet.remove(EPSILON);

        Map<Integer,Map<Character,Set<Integer>>> transitionMap = new HashMap<>();

        Map<Integer,String> stateLabelMap = new HashMap<>();
        Map<Set<Integer>,Integer> multipleStatesToSingleDfaIdMap = new HashMap<>();
        
        Set<Integer> dfaStartingStateSet = EpsilonClosure.epsilonClosure(Set.of(nfa.startState()), nfa.transitionMap());

        if(!Collections.disjoint(dfaStartingStateSet, nfa.acceptingStates())){
            acceptingStates.add(startState);
        }

        multipleStatesToSingleDfaIdMap.put(dfaStartingStateSet,startState); 
        states.add(startState);

        stateLabelMap.put(startState,subsetLabelGenerator(dfaStartingStateSet,nfa.stateLabelMap())); 

        Deque<Set<Integer>> unmarkedStates = new ArrayDeque<>();

        unmarkedStates.add(dfaStartingStateSet);

        while(!unmarkedStates.isEmpty()){

            Set<Integer> currentSetStates = unmarkedStates.pop();
            Integer currentId = multipleStatesToSingleDfaIdMap.get(currentSetStates);
            
            for(Character symbol: alphabet){
                Set<Integer> tempSet = new HashSet<>();

                for(Integer state : currentSetStates){

                    Set<Integer> targetStates = nfaTransitionLookup(state, symbol, nfa.transitionMap());
                    tempSet.addAll(targetStates);
                }
                
                Set<Integer> nextSet = EpsilonClosure.epsilonClosure(tempSet, nfa.transitionMap());

                if(!multipleStatesToSingleDfaIdMap.containsKey(nextSet)){
                    Integer nextId = idGen.next();
                    multipleStatesToSingleDfaIdMap.put(nextSet, nextId);
                    states.add(nextId);
                    unmarkedStates.push(nextSet);

                    stateLabelMap.put(nextId,subsetLabelGenerator(nextSet,nfa.stateLabelMap())); 
                    if(!Collections.disjoint(nextSet, nfa.acceptingStates())){
                    acceptingStates.add(nextId);
                    }
                }

                Integer targetId = multipleStatesToSingleDfaIdMap.get(nextSet);

                transitionMap.computeIfAbsent(currentId, r -> new HashMap<>()).computeIfAbsent(symbol,r-> new HashSet<>()).add(targetId);
            }
         }  
        return new DFA(startState,states,acceptingStates,alphabet,transitionMap,stateLabelMap);
    }


    /**
     * Gets the target states from a passed source state, symbol, and automata transitionMap
     * 
     * @param state source state to lookup
     * @param symbol symbol to lookup from source state
     * @param nfaTransitionMap automata transitionMap
     * @return target states from source and symbol
     */
    private static Set<Integer> nfaTransitionLookup(int state, char symbol, Map<Integer, Map<Character, Set<Integer>>> nfaTransitionMap){
        return nfaTransitionMap.getOrDefault(state, Collections.emptyMap()).getOrDefault(symbol, Collections.emptySet());
    }

    /**
     * Collects the NFA labels of states (e.g. "q1") for a set of NFA states from the NFA StateLabelMap, and joins into a string separated by commas and braces
     * 
     * if the subset is empty it returns "∅"
     * if there is no label for a state in the subset, "q"+ the id in the subset is mapped
     * 
     * @param subset set of NFA states to lookup in the NFA stateLabelMap
     * @param nfaStateLabelMap map of NFA states and their display labels e.g. <0,"q0">
     * @return combined string e.g. NFA subset of (0,1,2) may return "{q0,q1,q2}"
     */
    private static String subsetLabelGenerator(Set<Integer> subset, Map<Integer,String> nfaStateLabelMap){
        if(subset.isEmpty()){return "∅";}

        return subset.stream().sorted()
        .map(r -> {
            String label = nfaStateLabelMap.get(r);
            if(label == null ||label.isBlank()){
                return "q" + r;
            }
            return label;
        })
        .collect(Collectors.joining(",","{","}"));
    }
}
