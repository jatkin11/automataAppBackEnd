package com.jakeatkins.automataappbackend.dto;

/**
 * 
 * AutomataWordTest
 * 
 * DTO containing a ReactFlowGraph representation of an Automaton and a word to test
 * 
 * @param graph ReactFlowGraph of Automaton
 * @param word string of word to test on Automaton
 * 
 */

public record  AutomataWordTest(ReactFlowGraph graph, String word){}
