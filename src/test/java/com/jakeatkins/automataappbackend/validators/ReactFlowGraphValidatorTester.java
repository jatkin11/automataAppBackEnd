package com.jakeatkins.automataappbackend.validators;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.jakeatkins.automataappbackend.exceptions.InvalidReactFlowGraphException;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphValidGraph;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithADupeEdge;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithADupeNode;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithANodeWithANullId;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithANodeWithInvalidId;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithANodeWithNullLabel;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithANullEdge;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithANullNode;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithASelfLoop;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithAnEdgeWithNullId;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithAnIsolatedNode;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithEdgeContainingEpsilon;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithEdgeContainingMultipleSymbolsPerTransition;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithEdgeWithCommaSeparatedSymbolsInTransition;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithEdgeWithEmptyLabel;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithEdgeWithNullEdgeLabel;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithEdgeWithNullSource;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithEdgeWithNullTarget;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithEdgeWithUnknownSource;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithEdgeWithUnknownTarget;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithEmptyNodesList;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithInvalidSymbolsInATransition;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithMultipleStartingStates;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithNoEdges;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithNoStartingState;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithNullEdges;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithNullGraph;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithNullNodes;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithOneNodeGraph;

public class ReactFlowGraphValidatorTester {
 
    @Test
    void acceptsValidGraph(){
        assertDoesNotThrow(()-> ReactFlowGraphValidator.validate(reactFlowGraphValidGraph()));
    }
    
    @Test
    void acceptsGraphWithOneNode(){
        assertDoesNotThrow(()-> ReactFlowGraphValidator.validate(reactFlowGraphWithOneNodeGraph()));
    }

    @Test
    void rejectsNullGraph(){
        assertThrows(InvalidReactFlowGraphException.class,()-> ReactFlowGraphValidator.validate(reactFlowGraphWithNullGraph()));
    }

    @Test
    void rejectsNullNodes(){
        assertThrows(InvalidReactFlowGraphException.class,()-> ReactFlowGraphValidator.validate(reactFlowGraphWithNullNodes()));
    }

    @Test
    void rejectsNullEdges(){
      assertThrows(InvalidReactFlowGraphException.class,()-> ReactFlowGraphValidator.validate(reactFlowGraphWithNullEdges()));

    }

    @Test
    void RejectsEmptyNodes(){
        assertThrows(InvalidReactFlowGraphException.class,()-> ReactFlowGraphValidator.validate(reactFlowGraphWithEmptyNodesList()));
    }

    @Test
    void rejectsNullNode(){
        assertThrows(InvalidReactFlowGraphException.class,()-> ReactFlowGraphValidator.validate(reactFlowGraphWithANullNode()));
    }

    @Test
    void rejectsNodeWithNullId(){
        assertThrows(InvalidReactFlowGraphException.class,()-> ReactFlowGraphValidator.validate(reactFlowGraphWithANodeWithANullId()));
    }

    @Test
    void rejectsDupeNodes(){
        assertThrows(InvalidReactFlowGraphException.class,()-> ReactFlowGraphValidator.validate(reactFlowGraphWithADupeNode()));
    }

    @Test
    void rejectsInvalidNodeId(){
        assertThrows(InvalidReactFlowGraphException.class,()-> ReactFlowGraphValidator.validate(reactFlowGraphWithANodeWithInvalidId()));
    }
    
    @Test
    void rejectsNodesWithNullNodeLabels(){
        assertThrows(InvalidReactFlowGraphException.class,()-> ReactFlowGraphValidator.validate(reactFlowGraphWithANodeWithNullLabel()));
    }

    @Test
    void rejectsGraphWithNoStartingState(){
        assertThrows(InvalidReactFlowGraphException.class,()-> ReactFlowGraphValidator.validate(reactFlowGraphWithNoStartingState()));
    }

    @Test
    void rejectsGraphWithMoreThanOneStartingState(){
        assertThrows(InvalidReactFlowGraphException.class,()-> ReactFlowGraphValidator.validate(reactFlowGraphWithMultipleStartingStates()));
    }

    @Test
    void rejectsNullEdge(){
        assertThrows(InvalidReactFlowGraphException.class,()-> ReactFlowGraphValidator.validate(reactFlowGraphWithANullEdge()));
    }

    @Test
    void rejectsEdgesWithgNullEdgeIds(){
        assertThrows(InvalidReactFlowGraphException.class,()-> ReactFlowGraphValidator.validate(reactFlowGraphWithAnEdgeWithNullId()));
    }

    @Test
    void rejectsDupeEdges(){
        assertThrows(InvalidReactFlowGraphException.class,()-> ReactFlowGraphValidator.validate(reactFlowGraphWithADupeEdge()));
    }

    @Test
    void rejectsEdgesWithNullEdgeSource(){
        assertThrows(InvalidReactFlowGraphException.class,()-> ReactFlowGraphValidator.validate(reactFlowGraphWithEdgeWithNullSource()));
    }

    @Test
    void rejectsEdgesWithNullEdgeTarget(){
        assertThrows(InvalidReactFlowGraphException.class,()-> ReactFlowGraphValidator.validate(reactFlowGraphWithEdgeWithNullTarget()));
    }

    @Test
    void rejectsEdgesWithUnknownEdgeSource(){
        assertThrows(InvalidReactFlowGraphException.class,()-> ReactFlowGraphValidator.validate(reactFlowGraphWithEdgeWithUnknownSource()));
    }

    @Test
    void rejectsEdgesWithUnknownEdgeTarget(){
        assertThrows(InvalidReactFlowGraphException.class,()-> ReactFlowGraphValidator.validate(reactFlowGraphWithEdgeWithUnknownTarget()));
    }

    @Test
    void rejectsEdgesWithNullEdgeLabels(){
        assertThrows(InvalidReactFlowGraphException.class,()-> ReactFlowGraphValidator.validate(reactFlowGraphWithEdgeWithNullEdgeLabel()));
    }

    @Test
    void rejectsEdgesWithBlankEdgeLabels(){
        assertThrows(InvalidReactFlowGraphException.class,()-> ReactFlowGraphValidator.validate(reactFlowGraphWithEdgeWithEmptyLabel()));
    }

    @Test
    void acceptsEdgesWitghCommaSeparatedEdgeSymbols(){
        assertDoesNotThrow(()-> ReactFlowGraphValidator.validate(reactFlowGraphWithEdgeWithCommaSeparatedSymbolsInTransition()));
    }

    @Test
    void acceptsEdgesWithEpsilon(){
        assertDoesNotThrow(()-> ReactFlowGraphValidator.validate(reactFlowGraphWithEdgeContainingEpsilon()));
    }

    @Test
    void rejectsEdgesWithMoreThanOneSymbolPerTransition(){
        assertThrows(InvalidReactFlowGraphException.class,()-> ReactFlowGraphValidator.validate(reactFlowGraphWithEdgeContainingMultipleSymbolsPerTransition()));
    }

    @Test
    void rejectsEdgesWithInvalidSymbols(){
        assertThrows(InvalidReactFlowGraphException.class,()-> ReactFlowGraphValidator.validate(reactFlowGraphWithInvalidSymbolsInATransition()));
    }

    @Test
    void acceptsSelfLoopEdges(){
        assertDoesNotThrow(()-> ReactFlowGraphValidator.validate(reactFlowGraphWithASelfLoop()));
    }

    @Test
    void acceptsNodeWithoutEdges(){
        assertDoesNotThrow(()-> ReactFlowGraphValidator.validate(reactFlowGraphWithNoEdges()));
    }

    @Test
    void rejectsIsolatedNode(){
        assertThrows(InvalidReactFlowGraphException.class,()-> ReactFlowGraphValidator.validate(reactFlowGraphWithAnIsolatedNode()));
    }

}
