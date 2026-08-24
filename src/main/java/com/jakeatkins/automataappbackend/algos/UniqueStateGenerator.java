package com.jakeatkins.automataappbackend.algos;

import java.util.Objects;
import java.util.Set;

/**
 * 
 * UniqueStateGenerator
 * 
 * Generates a new state ID given a current set of state IDs
 * 
 * Returns next available positive int starting from 0 not contained in the set.
 * 
 */

public class UniqueStateGenerator {
    
    /**
     * Generates next positive int from 0
     * 
     * @param states set of states IDs of an automaton
     * @return next available int
     */
    public static int generate(Set<Integer> states){
        Objects.requireNonNull(states);

        int newState = 0;
        
        while(states.contains(newState)){
            newState++;
        }
        return newState;
    }
}
