package com.jakeatkins.automataappbackend.mappers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jakeatkins.automataappbackend.automata.DFA;
import com.jakeatkins.automataappbackend.automata.NFA;
import com.jakeatkins.automataappbackend.dto.ReactFlowEdge;
import com.jakeatkins.automataappbackend.dto.ReactFlowGraph;
import com.jakeatkins.automataappbackend.dto.ReactFlowNode;


public class ReactFlowGraphToAutomataMapper {
    

    //NEED TO ADD VALIDATION
    public static NFA reactFlowGraphToNFA(ReactFlowGraph graph){

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
                alphabet.add(symbol);
                transitionMap.computeIfAbsent(source, r-> new HashMap<>()).computeIfAbsent(symbol, r->new HashSet<>()).add(target);
            }
        }

        for(ReactFlowNode node : graph.getNodes()){
            
            Integer state = nodeIdToInt(node.getId());
            states.add(state);

            if(node.getData().isAcceptingState()){
                acceptingStates.add(state);
            }

            if(node.getData().isStartingState()){ //NEEDS VALIDATION TO ONLY HAVE ONE START STATE
                startState = state;
            }
            stateLabelMap.put(state,node.getData().getLabel());
        }
 
        return new NFA(startState, states, acceptingStates, alphabet, transitionMap, stateLabelMap);
    }


    //NEED TO REOMOVE DUPLICATOIN HERE, USE A HELPER FUNCTION TO TRANSLATE FROM REACT GRAPH TO EITHER NFA/DFA 
    public static DFA reactFlowGrapDfa(ReactFlowGraph graph){
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
                alphabet.add(symbol);
                transitionMap.computeIfAbsent(source, r-> new HashMap<>()).computeIfAbsent(symbol, r->new HashSet<>()).add(target);
            }
        }

        for(ReactFlowNode node : graph.getNodes()){
            
            Integer state = nodeIdToInt(node.getId());
            states.add(state);

            if(node.getData().isAcceptingState()){
                acceptingStates.add(state);
            }

            if(node.getData().isStartingState()){ //NEEDS VALIDATION TO ONLY HAVE ONE START STATE
                startState = state;
            }
            stateLabelMap.put(state,node.getData().getLabel());
        }
 
        return new DFA(startState, states, acceptingStates, alphabet, transitionMap, stateLabelMap);
    }


    //NEED TO ADD VALIDATION HERE
    private static Integer nodeIdToInt(String nodeId){
        return Integer.valueOf(nodeId.substring(1));
    }


    private static List<Character> reactFlowLabelToCharList(String label){ //NEED TO ADD VALIDATION HERE
        List<Character> charList = new ArrayList<>();
        String[] splitLabel = label.split(",");
        
        for(String x: splitLabel){
            charList.add(x.trim().charAt(0));
        }
        return charList; 
    } 
}
