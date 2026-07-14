package com.jakeatkins.automataappbackend.algos;

import java.util.Objects;
import java.util.Set;

public class UniqueStateGenerator {
    
    public static int generate(Set<Integer> states){
        Objects.requireNonNull(states);

        int newState = 0;
        
        while(states.contains(newState)){
            newState++;
        }
        return newState;
    }
}
