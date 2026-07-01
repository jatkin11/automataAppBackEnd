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


public class AutomataToReactFlowGraphMapper {
    
    private static final String EDGE_TYPE = "bezier";
    private record SourceTargetPairStates(Integer source, Integer target){};

    public static ReactFlowGraph fromAutomata(Automata automata){
        return new ReactFlowGraph(getAutomataType(automata),createReactFlowEdges(automata), createReactFlowNodes(automata));
    }

    public static List<ReactFlowEdge> createReactFlowEdges(Automata automata){
        List<ReactFlowEdge> edges = new ArrayList<>();
        Map<SourceTargetPairStates,Set<Character>> groupedLabelsPerSourceTargetMap = new HashMap<>();    
        
        for(Map.Entry<Integer, Map<Character,Set<Integer>>> mapSource : automata.getTransitionMap().entrySet()){

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

    public static List<ReactFlowNode> createReactFlowNodes(Automata automata){
        return automata.getStates()
        .stream().sorted()
        .map(r -> new ReactFlowNode(
            generateNodeId(r),
            new Position(100,100),
            new NodeData(
                automata.getLabel(r),
                automata.getStartState().equals(r),
                automata.getAcceptingStates().contains(r)
            ))
        ).toList();
    }

    private static String generateNodeId(Integer state){
        return "q" + state;
    }

    private static String generateEdgeId(Integer source, Integer target, String symbols){
        return generateNodeId(source) + "-" + symbols + "->" + generateNodeId(target);

    }

    //NEED TO ADD VALIDATION HERE
    private static String getAutomataType(Automata automata){
        return automata instanceof NFA ? "NFA" : "DFA";
    }

}
