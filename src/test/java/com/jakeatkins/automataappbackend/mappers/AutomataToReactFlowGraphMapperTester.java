package com.jakeatkins.automataappbackend.mappers;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.jakeatkins.automataappbackend.dto.ReactFlowEdge;

import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaComplex;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaSingleA;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithMultipleBranches;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithTwoEpsilonJumpThenA;
import static com.jakeatkins.automataappbackend.utilities.TestAutomataCreator.nfaWithStarredA;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphValidGraph;

public class AutomataToReactFlowGraphMapperTester {
    
    @Test
    void mapsNodes() {
        Set<String> testResult = AutomataToReactFlowGraphMapper.fromAutomata(nfaSingleA()).nodes().stream().map(r->r.id()).collect(Collectors.toSet());
        Set<String> expectedResult = nfaSingleA().states().stream().map(String::valueOf).collect(Collectors.toSet());

        assertEquals(expectedResult, testResult);
    }

    @Test
    void mapsStateLabels() {
        Set<String> testResult = AutomataToReactFlowGraphMapper.fromAutomata(nfaSingleA()).nodes().stream().map(r->r.data().label()).collect(Collectors.toSet());
        Set<String> expectedResult = nfaSingleA().stateLabelMap().values().stream().collect(Collectors.toSet());

        assertEquals(expectedResult, testResult);
    }

    @Test
    void mapsExactlyOneStartingState() {
        Long testResultSingleA = AutomataToReactFlowGraphMapper.fromAutomata(nfaSingleA()).nodes().stream().filter(r->r.data().startingState()).count();
        Long testResultComplex = AutomataToReactFlowGraphMapper.fromAutomata(nfaComplex()).nodes().stream().filter(r->r.data().startingState()).count();
        Long testResultMultipleAcceptingStates = AutomataToReactFlowGraphMapper.fromAutomata(nfaWithMultipleBranches()).nodes().stream().filter(r->r.data().startingState()).count();
        
        assertEquals(1, testResultSingleA);
        assertEquals(1, testResultComplex);
        assertEquals(1, testResultMultipleAcceptingStates);

    }

    @Test
    void mapsAcceptingNodes() {
        Set<String> testResultSingleA = AutomataToReactFlowGraphMapper.fromAutomata(nfaSingleA()).nodes().stream().filter(r->r.data().acceptingState()).map(r->r.id()).collect(Collectors.toSet());
        Set<String> testResultComplex = AutomataToReactFlowGraphMapper.fromAutomata(nfaComplex()).nodes().stream().filter(r->r.data().acceptingState()).map(r->r.id()).collect(Collectors.toSet());
        Set<String> testResultMultipleAcceptingStates = AutomataToReactFlowGraphMapper.fromAutomata(nfaWithMultipleBranches()).nodes().stream().filter(r->r.data().acceptingState()).map(r->r.id()).collect(Collectors.toSet());
        
        Set<String> expectedSingleA = nfaSingleA().acceptingStates().stream().map(String::valueOf).collect(Collectors.toSet());
        Set<String> expectedComplex = nfaComplex().acceptingStates().stream().map(String::valueOf).collect(Collectors.toSet());
        Set<String> expectedMultipleAcceptingStates = nfaWithMultipleBranches().acceptingStates().stream().map(String::valueOf).collect(Collectors.toSet());

        assertEquals(expectedSingleA, testResultSingleA);
        assertEquals(expectedComplex, testResultComplex);
        assertEquals(expectedMultipleAcceptingStates, testResultMultipleAcceptingStates);
    }

    @Test
    void mapsEdgePerTransition() {
        Long testResultSingleA = AutomataToReactFlowGraphMapper.fromAutomata(nfaSingleA()).edges().stream().count();
        Long testResultComplex = AutomataToReactFlowGraphMapper.fromAutomata(nfaComplex()).edges().stream().count();
        Long testResultMultipleAcceptingStates = AutomataToReactFlowGraphMapper.fromAutomata(nfaWithMultipleBranches()).edges().stream().count();

        assertEquals(1, testResultSingleA);
        assertEquals(11, testResultComplex);
        assertEquals(2, testResultMultipleAcceptingStates);
    }

    @Test
    void mapsEpislon() {
        Long testResult = AutomataToReactFlowGraphMapper.fromAutomata(nfaWithTwoEpsilonJumpThenA()).edges().stream().filter(r->r.label().equals("ε")).count();
         assertEquals(2, testResult);
    }

    @Test
    void mapsStarred() {
        ReactFlowEdge selfLoop = AutomataToReactFlowGraphMapper.fromAutomata(nfaWithStarredA()).edges().stream().filter(r->r.source().equals(r.target())).findFirst().orElseThrow();
        
        assertEquals("1", selfLoop.source());
        assertEquals("1", selfLoop.target());
        assertEquals("a", selfLoop.label());
    }

    @Test
    void correctlyMapsComplexAutomataToReactFlowGraph() {
        assertEquals(reactFlowGraphValidGraph(), AutomataToReactFlowGraphMapper.fromAutomata(nfaSingleA()));
    }

}
