package com.jakeatkins.automataappbackend.algos;

public class GlobalStateIdGenerator {
    
    private  int nextId = 0;

    public int next(){
        return nextId++;
    }

}
