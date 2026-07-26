package com.jakeatkins.automataappbackend.algos;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.jakeatkins.automataappbackend.automata.DFA;
import com.jakeatkins.automataappbackend.automata.NFA;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.dfaSingleA;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaSingleA;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithStarredA;


public class WordTesterTester {
    
    @Test
    void acceptsAonNfa(){
        NFA nfa = nfaSingleA();
        assertTrue(WordTester.testAutomata(nfa, "a"));
        assertFalse(WordTester.testAutomata(nfa, "b"));
        assertFalse(WordTester.testAutomata(nfa, ""));
    }


    @Test
    void acceptsAonDfa(){
        DFA dfa = dfaSingleA();
        assertTrue(WordTester.testAutomata(dfa, "a"));
        assertFalse(WordTester.testAutomata(dfa, "b"));
    }

    @Test
    void acceptsStarredAonNfa(){
        NFA nfa = nfaWithStarredA();
        assertTrue(WordTester.testAutomata(nfa, "a"));
        assertTrue(WordTester.testAutomata(nfa, "aa"));
        assertTrue(WordTester.testAutomata(nfa, "aaa"));
        assertTrue(WordTester.testAutomata(nfa, "aaaa"));
        assertFalse(WordTester.testAutomata(nfa, "b"));
        assertFalse(WordTester.testAutomata(nfa, "bb"));
        assertTrue(WordTester.testAutomata(nfa, ""));
    }






}
