package com.jakeatkins.automataappbackend.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.jakeatkins.automataappbackend.dto.AutomataWordTest;
import com.jakeatkins.automataappbackend.dto.ReactFlowGraph;
import com.jakeatkins.automataappbackend.dto.RegexString;
import com.jakeatkins.automataappbackend.dto.RegexWordTest;
import com.jakeatkins.automataappbackend.exceptions.InvalidAutomataWordTestException;
import com.jakeatkins.automataappbackend.exceptions.InvalidReactFlowGraphException;
import com.jakeatkins.automataappbackend.exceptions.InvalidRegexException;
import com.jakeatkins.automataappbackend.exceptions.InvalidRegexStringException;
import com.jakeatkins.automataappbackend.exceptions.InvalidRegexWordTestException;
import com.jakeatkins.automataappbackend.exceptions.InvalidWordException;
import com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator;
import com.jakeatkins.automataappbackend.utilities.TestRegexStringCreator;

public class AutomataServiceTester {
    

    @Test
    void convertToDfaServiceConvertsValidGraph(){
        AutomataService service = new AutomataService();

        ReactFlowGraph graph = service.convertToDfa(TestReactFlowGraphCreator.reactFlowGraphValidGraph());
        assertDoesNotThrow(()->service.convertToDfa(TestReactFlowGraphCreator.reactFlowGraphValidGraph()));
        assertNotNull(graph);
        assertEquals("DFA", graph.automataType());

    }


    @Test
    void convertToNfaServiceConvertsValidRegex(){
        AutomataService service = new AutomataService();
        ReactFlowGraph graph = service.convertToNfa(TestRegexStringCreator.simpleValidRegexString());

        assertDoesNotThrow(()-> service.convertToNfa(TestRegexStringCreator.simpleValidRegexString()));
        assertNotNull(graph);
        assertEquals("NFA", graph.automataType());
    }

    @Test
    void convertToRegexStringServiceConvertsAutomata(){
        AutomataService service = new AutomataService();

        RegexString regex = service.convertToRegexString(TestReactFlowGraphCreator.reactFlowGraphValidGraph());
        assertDoesNotThrow(()-> service.convertToRegexString(TestReactFlowGraphCreator.reactFlowGraphValidGraph()));
        
        assertNotNull(regex);

        assertEquals("a", regex.regex());
        
    }


    @Test
    void testWordOnRegexStringServiceReturnsTrueForValidAndTrueTest(){
        AutomataService service = new AutomataService();

        RegexWordTest wordTestTrue = new RegexWordTest("abc","abc");
        RegexWordTest wordTestFalse = new RegexWordTest("a", "abc");

        assertTrue(service.testWordOnRegexString(wordTestTrue).accepted());
        assertFalse(service.testWordOnRegexString(wordTestFalse).accepted());
        
    }

    @Test
    void testWordOnAutomataServiceReturnsTrueForValidAndTrueTest(){
        AutomataService service = new AutomataService();

        ReactFlowGraph graph = TestReactFlowGraphCreator.reactFlowGraphComplex();

        AutomataWordTest wordTestTrue = new AutomataWordTest(graph,"abb");
        AutomataWordTest wordTestFalse = new AutomataWordTest(graph, "xxx");

        assertTrue(service.testWordOnAutomata(wordTestTrue).accepted());
        assertFalse(service.testWordOnAutomata(wordTestFalse).accepted());
        
    }


    @Test
    void invalidReactFlowGraphExceptionsBubbleUp(){
        AutomataService service = new AutomataService();
        
        assertThrows(InvalidReactFlowGraphException.class,()-> service.convertToDfa(null));
    }

    @Test
    void invalidAutomataExceptionsBubbleUp(){
        AutomataService service = new AutomataService();
        

        assertThrows(InvalidReactFlowGraphException.class,()-> service.convertToDfa(TestReactFlowGraphCreator.reactFlowGraphWithANodeWithANullId()));

    }


    @Test
    void invalidRegexStringExpcetionsBubbleUp(){
        AutomataService service = new AutomataService();
        
        assertThrows(InvalidRegexStringException.class,()->service.convertToNfa(null) );
    }

    @Test
    void invalidWordExceptionsBubbleUp(){
        AutomataService service = new AutomataService();
        assertThrows(InvalidWordException.class,()-> service.testWordOnRegexString(new RegexWordTest("&","abc")));
    }


    @Test
    void invalidAutomataWordTestExceptionsBubbleUp(){
        AutomataService service = new AutomataService();
        assertThrows(InvalidAutomataWordTestException.class,()-> service.testWordOnAutomata(null));
    }

    @Test
    void invalidRegexExceptionsBubbleUp(){
        AutomataService service = new AutomataService();
        assertThrows(InvalidRegexException.class,()-> service.testWordOnRegexString(new RegexWordTest("abc","&&")));

    }

    @Test
    void invalidRegexWordTestExceptionsBubbleUp(){
        AutomataService service = new AutomataService();
        assertThrows(InvalidRegexWordTestException.class,()->service.testWordOnRegexString(new RegexWordTest("a",null)));
    }


}
