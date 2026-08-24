package com.jakeatkins.automataappbackend.mappers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.jakeatkins.automataappbackend.automata.Automata;
import com.jakeatkins.automataappbackend.automata.NFA;
import com.jakeatkins.automataappbackend.dto.NodeData;
import com.jakeatkins.automataappbackend.dto.Position;
import com.jakeatkins.automataappbackend.dto.ReactFlowEdge;
import com.jakeatkins.automataappbackend.dto.ReactFlowGraph;
import com.jakeatkins.automataappbackend.dto.ReactFlowNode;
import com.jakeatkins.automataappbackend.validators.AutomataValidator;

/**
 * 
 * AutomataToReactFlowGraphMapper
 * 
 * Maps Automata to ReactFlowGraph DTO
 * 
 */

public class AutomataToReactFlowGraphMapper {
    
    /**
     * Edge and Node custom types used for front-end processing
     */
    private static final String NODE_TYPE = "custom";
    private static final String EDGE_TYPE = "default";

    /**
     * Record to group source and target pairs
     * SourceTargetPairStates
     * @param source source state ID
     * @param target target state ID
     */
    private record SourceTargetPairStates(Integer source, Integer target){};

    /**
     * Maps Automata to ReactFlowGraph
     * 
     * - validates Automata
     * - constructs new ReactFlowGraph(automataType,List<ReactFlowEdge>,List<ReactFlowNode>)
     *  
     * @param automata Automata to map
     * @return new ReactFlowGraph
     */
    public static ReactFlowGraph fromAutomata(Automata automata){
        AutomataValidator.validate(automata);
        return new ReactFlowGraph(getAutomataType(automata),createReactFlowEdges(automata), createReactFlowNodes(automata));
    }

    /**
     * Creates list of ReactFlowEdges 
     * 
     * Process:
     * - cycles through Automata transitionMap:
     *      - for every source-target pair:
     *          - create new SourceTargetPairStates e.g. (0,1)
     *          - add SourceTargetPairStates and all symbols for that pair to groupedLabelsPerSourceTargetMap e.g. <(0,1),[a,b]>
     * 
     * - cycle through groupedLabelsPerSourceTargetMap:
     *      - for every source target pair:
     *          - create a grouped label string by combining with comma e.g. [a,b] -> "a,b"
     *          - construct new ReactFlowEdge with edgeID, source, target, grouped label string, and edge type
     * 
     * @param automata Automata to map
     * @return List of ReactFlowEdges
     */
    private static List<ReactFlowEdge> createReactFlowEdges(Automata automata){
        List<ReactFlowEdge> edges = new ArrayList<>();
        Map<SourceTargetPairStates,Set<Character>> groupedLabelsPerSourceTargetMap = new HashMap<>();    
        
        for(Map.Entry<Integer, Map<Character,Set<Integer>>> mapSource : automata.transitionMap().entrySet()){

            Integer source = mapSource.getKey();
            
            Map<Character,Set<Integer>> transitionFromMapSource = mapSource.getValue();

            for(Map.Entry<Character,Set<Integer>> mapSymbol: transitionFromMapSource.entrySet()){

                char symbol = mapSymbol.getKey();

                Set<Integer> transitionToMapTarget = mapSymbol.getValue();

                for(Integer target : transitionToMapTarget){

                SourceTargetPairStates temp = new SourceTargetPairStates(source,target);

                groupedLabelsPerSourceTargetMap.computeIfAbsent(temp, r -> new HashSet<>()).add(symbol);
                }
            }
        }

        for(Map.Entry<SourceTargetPairStates,Set<Character>> pair: groupedLabelsPerSourceTargetMap.entrySet()){
            
            SourceTargetPairStates sourceTarget = pair.getKey();
            Integer pairSource = sourceTarget.source();
            Integer pairTarget = sourceTarget.target();
            Set<Character> groupedLabels = pair.getValue();
            String groupedLabelsString = groupedLabels.stream().sorted().map(r->String.valueOf(r)).collect(Collectors.joining(","));

            ReactFlowEdge e = new ReactFlowEdge(
                generateEdgeId(pairSource, pairTarget, groupedLabelsString),
                generateNodeId(pairSource),
                generateNodeId(pairTarget),
                groupedLabelsString,
                EDGE_TYPE
            );
            edges.add(e);
        }

        return edges;
    }

    /**
     * Creates ReactFlowNodes from Automata
     * 
     * Process:
     * - streams Automata set of states
     * - sorts
     * - maps each state to a new ReactFlowNode:
     *      - node ID using generateNodeId helper
     *      - manually creating position data (could be removed on a refactor, as front-end overrides node positions)
     *      - label using the nodes .getLabel
     *      - sets starting state if equal to Automata starting state
     *      - sets accepting state if Automata accepinting state contains node
     * 
     * @param automata Automata to map
     * @return List of ReactFlowNodes
     */
    private static List<ReactFlowNode> createReactFlowNodes(Automata automata){
        return automata.states()
        .stream().sorted()
        .map(r -> new ReactFlowNode(
            generateNodeId(r),
            new Position(100*r,100*r),
            new NodeData(
                automata.getLabel(r),
                automata.startState().equals(r),
                automata.acceptingStates().contains(r)
            ),NODE_TYPE)
            
        ).toList();
    }

    /**
     * Helper method - converts state ID into string ID
     * @param state state ID to convert
     * @return String of State ID
     */
    private static String generateNodeId(Integer state){
        return String.valueOf(state);
    }

    /**
     * Helper method - creates Edge ID
     * 
     * Combines source ID + transition symbols label + target ID
     * 
     * @param source source state ID
     * @param target target state ID
     * @param symbols transition symbols string
     * @return unique edge ID
     */
    private static String generateEdgeId(Integer source, Integer target, String symbols){
        return generateNodeId(source) + "-" + symbols + "->" + generateNodeId(target);

    }


    /**
     * Helper method - checks concrete class of Automata and returns string of type
     * 
     * @param automata Automata to be checked
     * @return string of Automata type (DFA/NFA)
     */
    private static String getAutomataType(Automata automata){
        return automata instanceof NFA ? "NFA" : "DFA";
    }

}
