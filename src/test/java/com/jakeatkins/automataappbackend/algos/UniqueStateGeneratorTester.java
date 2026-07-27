package com.jakeatkins.automataappbackend.algos;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class UniqueStateGeneratorTester {
    
    @Test
    void returnsZeroForEmptySet(){
        assertEquals(0,UniqueStateGenerator.generate(Collections.emptySet()));
    }

    @Test
    void returnsNextUnusedNonNegInt(){
        assertEquals(3,UniqueStateGenerator.generate(Set.of(0,1,2,6)));
        assertEquals(4,UniqueStateGenerator.generate(Set.of(0,1,2,3)));
        assertEquals(0,UniqueStateGenerator.generate(Set.of(2,3)));
        assertEquals(0,UniqueStateGenerator.generate(Set.of(1)));
    }

    @Test
    void ignoresNegativeInts(){
        assertEquals(0,UniqueStateGenerator.generate(Set.of(-1,-2,-4)));
    }

    @Test
    void throwsErrorOnNull(){
        assertThrows(NullPointerException.class,()-> UniqueStateGenerator.generate(null));
        
    }
 
    
}
