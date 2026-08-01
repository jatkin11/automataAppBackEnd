package com.jakeatkins.automataappbackend.mappers;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaComplex;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaSingleA;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaVersionOfReactFlowGraphWithCommaSeparatedValues;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithEpsilonOnly;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithSelfLoop;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphComplex;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphEpsilonOnly;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphValidGraph;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithASelfLoop;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithEdgeWithCommaSeparatedSymbolsInTransition;

public class ReactFlowGraphToAutomataMapperTester {
    
    @Test
    void mapsNodeIdsToStates(){
        assertEquals(reactFlowGraphValidGraph().nodes().stream()
            .map(r->r.id()).map(Integer::parseInt)
            .collect(Collectors.toSet()), 
            ReactFlowGraphToAutomataMapper.reactFlowGraphToNFA(reactFlowGraphValidGraph())
            .states());
    }

    @Test
    void mapsStartingNode(){
        assertEquals(reactFlowGraphValidGraph()
            .nodes().stream()
            .filter(r-> r.data()
            .startingState())
            .map(r->r.id())
            .map(Integer::parseInt)
            .findFirst().orElseThrow(), 
            ReactFlowGraphToAutomataMapper.reactFlowGraphToNFA(reactFlowGraphValidGraph())
            .startState());
    }

    @Test
    void mapsAcceptingNodes(){
        assertEquals(reactFlowGraphValidGraph()
            .nodes().stream()
            .filter(r-> r.data()
            .acceptingState())
            .map(r->r.id())
            .map(Integer::parseInt)
            .collect(Collectors.toSet()),
            ReactFlowGraphToAutomataMapper.reactFlowGraphToNFA(reactFlowGraphValidGraph())
            .acceptingStates());
    }

    @Test
    void mapsStateLabels(){
        assertEquals(reactFlowGraphValidGraph()
            .nodes().stream()
            .map(r->r.data().label())
            .collect(Collectors.toSet()),
            ReactFlowGraphToAutomataMapper.reactFlowGraphToNFA(reactFlowGraphValidGraph())
            .stateLabelMap().values().stream().collect(Collectors.toSet()));
    }

    @Test
    void mapsSingleSymbolTransition(){
        assertEquals(nfaSingleA(), ReactFlowGraphToAutomataMapper.reactFlowGraphToNFA(reactFlowGraphValidGraph()));
    }

    @Test
    void mapsCommaSeparatedTransition(){
        assertEquals(nfaVersionOfReactFlowGraphWithCommaSeparatedValues(), ReactFlowGraphToAutomataMapper.reactFlowGraphToNFA(reactFlowGraphWithEdgeWithCommaSeparatedSymbolsInTransition()));
    }

    @Test
    void mapsEpislonTransition(){
        assertEquals(nfaWithEpsilonOnly(), ReactFlowGraphToAutomataMapper.reactFlowGraphToNFA(reactFlowGraphEpsilonOnly()));
        
    }

    @Test
    void mapsStarredTransition(){
        assertEquals(nfaWithSelfLoop(), ReactFlowGraphToAutomataMapper.reactFlowGraphToNFA(reactFlowGraphWithASelfLoop()));
        
    }

    @Test
    void mapsAlphabet(){
        assertEquals(Set.of('a','b','c'), ReactFlowGraphToAutomataMapper.reactFlowGraphToNFA(reactFlowGraphWithEdgeWithCommaSeparatedSymbolsInTransition()).alphabet());
    }

    @Test
    void correctlyMapsComplexReactFlowGraphToAutomata(){
        assertEquals(nfaComplex(), ReactFlowGraphToAutomataMapper.reactFlowGraphToNFA(reactFlowGraphComplex()));
    }

}
