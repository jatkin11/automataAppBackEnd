package com.jakeatkins.automataappbackend.mappers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.EPSILON;
import com.jakeatkins.automataappbackend.automata.NFA;
import com.jakeatkins.automataappbackend.dto.ReactFlowEdge;
import com.jakeatkins.automataappbackend.dto.ReactFlowGraph;
import com.jakeatkins.automataappbackend.dto.ReactFlowNode;
import com.jakeatkins.automataappbackend.exceptions.InvalidReactFlowGraphException;
import com.jakeatkins.automataappbackend.validators.ReactFlowGraphValidator;

/**
 * 
 * ReactFlowGraphToAutomataMapper
 * 
 * Maps from ReactFlowGraph DTO to new NFA
 */
public class ReactFlowGraphToAutomataMapper {
    
    /**
     * ReactFlowGraph to NFA mapping
     * 
     * Process:
     * - Validates graph using ReactFlowGraphValidator
     * - instantiates new NFA components
     * - for every ReactFlowEdge in the ReactFlowGraph:
     *      - splits label string into chars list
     *      - gets source and target state ID ints
     *      for symbol in chars list:
     *          - if not Epsilon, adds to new NFA alphabet set
     *          - adds new transition to new NFA transitionMap from source to target IDs (a label of "a,b" would create 2 transitions of 'a' and 'b')
     * - for every ReactFlowNode in ReactFlowGraph:
     *      - add node to new NFA states
     *      - maps accepting/starting states from graph to new NFA
     *      - adds node ID and display label to new NFA stateLabelMap  
     * 
     * @param graph ReactFlowGraph to convert
     * @return converted NFA
     */
    public static NFA reactFlowGraphToNFA(ReactFlowGraph graph){

        ReactFlowGraphValidator.validate(graph);

        Set<Integer> states = new HashSet<>();
        Integer startState = null;
        Set<Integer> acceptingStates = new HashSet<>();
        Set<Character> alphabet = new HashSet<>();
        Map<Integer,Map<Character,Set<Integer>>> transitionMap = new HashMap<>();
        Map<Integer,String> stateLabelMap = new HashMap<>();

        for(ReactFlowEdge edge: graph.edges()){
            List<Character> splitSymbols = reactFlowLabelToCharList(edge.label());
            Integer source = nodeIdToInt(edge.source());
            Integer target = nodeIdToInt(edge.target());

            for(Character symbol : splitSymbols){
                if(symbol != EPSILON){
                alphabet.add(symbol);}
                transitionMap.computeIfAbsent(source, r-> new HashMap<>()).computeIfAbsent(symbol, r->new HashSet<>()).add(target);
            }
        }

        for(ReactFlowNode node : graph.nodes()){
            
            Integer state = nodeIdToInt(node.id());
            states.add(state);

            if(node.data().acceptingState()){
                acceptingStates.add(state);
            }

            if(node.data().startingState()){ 
                startState = state;
            }
            stateLabelMap.put(state,node.data().label());
        }
 
        return new NFA(startState, states, acceptingStates, alphabet, transitionMap, stateLabelMap);
    }

    /**
     * Helper method - Parses String ID from ReactFlowGraph to Integer
     * 
     * Validates:
     * - string ID not null
     * 
     * @param nodeId string ID
     * @return parsed ID int
     */
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

    /**
     * Helper method - converts transition label comma separated symbol string to list of characters
     * 
     * Validates:
     * - label not null
     * - label not blank
     * - symbols are 1 char
     * - valid chars i.e. A-Z, a-z, 0-9, ε
     * - strips whitespace
     * 
     * @param label ReactFlowEdge label e.g. "a,b,c"
     * @return list of Chars from label e.g. ['a','b','c']
     */
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
