package com.jakeatkins.automataappbackend.algos;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.EPSILON;
import com.jakeatkins.automataappbackend.exceptions.InvalidAutomataException;

/**
 * 
 * EpsilonClosure
 * 
 * Calculates the epsilon closure from a single state or set of states, from a given transitionMap
 * 
 */
public class EpsilonClosure {


    /**
     * 
     * Calculates the epsilon closure of a set of states, from a given transitionMap
     * 
     * Goes through each state in a set of states and uses the epsilonClosurePerState to calculate all states reachable via 0 or more epsilon-transitions
     *  
     * @param states set of states 
     * @param transitionMap automata transitionMap
     * @return epsilon closure set of states
     * @throws InvalidAutomataException if states or transitionMap is null
     */
    public static Set<Integer> epsilonClosure(Set<Integer> states, Map<Integer, Map<Character,Set<Integer>>> transitionMap){
        
        if(states== null){
            throw new InvalidAutomataException("States for Epsilon closure cannot be null");
        }

        if(transitionMap == null){
            throw new InvalidAutomataException("Transition map for Epsilon closure cannot be null");
        }
        
        Set<Integer> closure = new HashSet<>();

        for(Integer i: states){
            closure.addAll(epsilonClosurePerState(i, transitionMap));
        }

        return closure;

    }

    /**
     * 
     * Calculates epsilon closure on a single state, from a given transitionMap
     * 
     * Adapted from the pseudocode found in report chapter 4.3B (Norton, 2009)
     * 
     * Uses a stack to follow epsilon-transitions, if closure set doesn't contain a reached state it is added to the closure set
     * and to the stack.
     * 
     * 
     * @param state state 
     * @param transitionMap automata transitionMap
     * @return epsilon closure set of states
     */
    private static Set<Integer> epsilonClosurePerState(Integer state, Map<Integer,Map<Character,Set<Integer>>> transitionMap){
        Set<Integer> closurePerState = new HashSet<>();
        Deque<Integer> stack = new ArrayDeque<>();

        closurePerState.add(state);
        stack.add(state);

        while(!stack.isEmpty()){
            int currentState = stack.pop();

            for(Integer i: transitionMap.getOrDefault(currentState, Collections.emptyMap()).getOrDefault(EPSILON,Collections.emptySet())){
                if(!closurePerState.contains(i)){
                    closurePerState.add(i);
                    stack.add(i);
                }
            }
        }
        return closurePerState;

    }

}