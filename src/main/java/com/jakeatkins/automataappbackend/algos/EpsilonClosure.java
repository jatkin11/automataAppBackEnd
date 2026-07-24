package com.jakeatkins.automataappbackend.algos;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.EPSILON;
import com.jakeatkins.automataappbackend.exceptions.*;


public class EpsilonClosure {

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