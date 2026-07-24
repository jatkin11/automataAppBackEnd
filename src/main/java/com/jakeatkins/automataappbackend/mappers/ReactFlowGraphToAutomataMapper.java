package com.jakeatkins.automataappbackend.mappers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Arrays;

import com.jakeatkins.automataappbackend.automata.NFA;
import com.jakeatkins.automataappbackend.dto.*;
import com.jakeatkins.automataappbackend.validators.*;
import com.jakeatkins.automataappbackend.exceptions.*;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.EPSILON;

public class ReactFlowGraphToAutomataMapper {
    
    public static NFA reactFlowGraphToNFA(ReactFlowGraph graph){

        ReactFlowGraphValidator.validate(graph);

        Set<Integer> states = new HashSet<>();
        Integer startState = null;
        Set<Integer> acceptingStates = new HashSet<>();
        Set<Character> alphabet = new HashSet<>();
        Map<Integer,Map<Character,Set<Integer>>> transitionMap = new HashMap<>();
        Map<Integer,String> stateLabelMap = new HashMap<>();

        for(ReactFlowEdge edge: graph.getEdges()){
            List<Character> splitSymbols = reactFlowLabelToCharList(edge.getLabel());
            Integer source = nodeIdToInt(edge.getSource());
            Integer target = nodeIdToInt(edge.getTarget());

            for(Character symbol : splitSymbols){
                if(symbol != EPSILON){
                alphabet.add(symbol);}
                transitionMap.computeIfAbsent(source, r-> new HashMap<>()).computeIfAbsent(symbol, r->new HashSet<>()).add(target);
            }
        }

        for(ReactFlowNode node : graph.getNodes()){
            
            Integer state = nodeIdToInt(node.getId());
            states.add(state);

            if(node.getData().isAcceptingState()){
                acceptingStates.add(state);
            }

            if(node.getData().isStartingState()){ 
                startState = state;
            }
            stateLabelMap.put(state,node.getData().getLabel());
        }
 
        return new NFA(startState, states, acceptingStates, alphabet, transitionMap, stateLabelMap);
    }

    private static Integer nodeIdToInt(String nodeId){
        if(nodeId == null){
            throw new InvalidReactFlowGraphException("Invalid React Flow Graph: Node id cannot be null");
        }
        try{
            return Integer.valueOf(nodeId);
        }catch(NumberFormatException e){
            throw new InvalidReactFlowGraphException("Invalid React Flow graph: Node id must be an int");
        }
    }

    private static List<Character> reactFlowLabelToCharList(String label){
        if(label == null){
            throw new InvalidReactFlowGraphException("Invalid React Flow graph: label cannot be null");
        }
        if(label.isBlank()){
            throw new InvalidReactFlowGraphException("Invalid React Flow graph: label cannot be blank");
        }
        return Arrays.stream(label.split(",")).map(String::strip).map(r-> {
            if(r.length() != 1){
            throw new InvalidReactFlowGraphException("Invalid React Flow graph: label must only contain single symbols separated by commas");
            }
            if(!r.matches("^[A-Za-z0-9ε]$")){
            throw new InvalidReactFlowGraphException("Invalid React Flow graph: label must only contain symobsl A-Z,a-z,0-9, or ε"); 
            }
            return r.charAt(0);}
        ).toList();
    } 
}
