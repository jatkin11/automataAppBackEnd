package com.jakeatkins.automataappbackend.algos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class GlobalStateIdGeneratorTester {
    
    @Test
    void generatesStateIdsSequentiallyFromZero(){
        GlobalStateIdGenerator idGen = new GlobalStateIdGenerator();
        assertEquals(0,idGen.next());
        assertEquals(1,idGen.next());
        assertEquals(2,idGen.next());

    }

}
