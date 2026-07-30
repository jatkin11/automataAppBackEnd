package com.jakeatkins.automataappbackend.algos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaSingleA;

public class RegexToNfaConverterTester {
    
    @Test
    void convertsSingleA(){
        assertEquals(nfaSingleA(), new RegexToNfaConverter(new RegexTokeniser("a").tokenise()).convert());
    }

    @Test
    void convertsConcatnAB(){
        
    }

    @Test
    void convertsUnionAB(){
        
    }

    @Test
    void convertsAStarred(){
        
    }

    @Test
    void convertsGrouped(){
        
    }

    @Test
    void convertsNested(){
        
    }

    @Test
    void convertsComplexRegex(){
        
    }

    @Test
    void convertsEpsilon(){
        
    }

    @Test
    void convertsEmptySet(){
        
    }

    @Test
    void correctAlphabet(){
        
    }

    @Test
    void correctStartState(){
        
    }

    @Test
    void correctAcceptingStates(){
        
    }

    @Test
    void correctStateLabels(){
        
    }

    @Test
    void correctEdgeSources(){
        
    }

    @Test
    void correctEdgesTargets(){
        
    }

    @Test
    void correctStateIds(){
        
    }

    @Test
    void correctGlushkovPositionsOnRegex(){
        
    }

}
