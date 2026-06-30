package com.jakeatkins.automataappbackend.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jakeatkins.automataappbackend.automata.Automata;
import com.jakeatkins.automataappbackend.automata.NFA;
import com.jakeatkins.automataappbackend.dto.NodeData;
import com.jakeatkins.automataappbackend.dto.Position;
import com.jakeatkins.automataappbackend.dto.ReactFlowEdge;
import com.jakeatkins.automataappbackend.dto.ReactFlowGraph;
import com.jakeatkins.automataappbackend.dto.ReactFlowNode;


public class AutomataToReactFlowGraphMapper {
    
    private static final String edgeType = "bezier";

    public static ReactFlowGraph fromAutomata(Automata automata){
        return new ReactFlowGraph(getAutomataType(automata),createReactFlowEdges(automata), createReactFlowNodes(automata));
    }

    public static List<ReactFlowEdge> createReactFlowEdges(Automata automata){
        List<ReactFlowEdge> edges = new ArrayList<>();    
        
        for(Map.Entry<Integer, Map<Character,Set<Integer>>> mapSource : automata.getTransitionMap().entrySet()){

            Integer source = mapSource.getKey();
            
            Map<Character,Set<Integer>> transitionFromMapSource = mapSource.getValue();

            for(Map.Entry<Character,Set<Integer>> mapSymbol: transitionFromMapSource.entrySet()){

                char symbol = mapSymbol.getKey();

                Set<Integer> transitionToMapTarget = mapSymbol.getValue();

                for(Integer target : transitionToMapTarget){

                    ReactFlowEdge e = new ReactFlowEdge(
                        generateEdgeId(source, target, symbol),
                        generateNodeId(source),
                        generateNodeId(target),
                        String.valueOf(symbol),
                        edgeType
                    );
                    edges.add(e);
                }
            }
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

    private static String generateEdgeId(Integer source, Integer target, char symbol){
        return generateNodeId(source) + "-" + symbol + "->" + generateNodeId(target);

    }

    private static String getAutomataType(Automata automata){
        return automata instanceof NFA ? "NFA" : "DFA";
    }

}
