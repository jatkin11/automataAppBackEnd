package com.jakeatkins.automataappbackend.algos;

/**
 * 
 * GlobalStateIdGenerator
 * 
 * Generates a new ID starting at 0, non-static to provide sequential ints
 */
public class GlobalStateIdGenerator {
    
    private int nextId = 0;

    /**
     * Provides new state ID starting at 0 and incrementing every times its called
     * @return next int
     */
    public int next(){
        return nextId++;
    }

}
