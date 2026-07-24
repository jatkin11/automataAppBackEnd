package com.jakeatkins.automataappbackend.validators;

import com.jakeatkins.automataappbackend.dto.*;
import java.util.*;
import java.util.stream.Collectors;
import com.jakeatkins.automataappbackend.exceptions.*;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.EPSILON;

public class ReactFlowGraphValidator {
    
    public static void validate(ReactFlowGraph rfg){
        validateGraph(rfg);
    }

    private static void validateGraph(ReactFlowGraph rfg){
        if(rfg == null){
            throw new InvalidReactFlowGraphException("React Flow Graph cannot be null");
        }

        if(rfg.getNodes()==null){
            throw new InvalidReactFlowGraphException("React Flow Graph nodes cannot be null");
        }

        if(rfg.getEdges()==null){
            throw new InvalidReactFlowGraphException("React Flow Graph edges cannot be null");
        }

        if(rfg.getNodes().size() < 1){
            throw new InvalidReactFlowGraphException("React Flow Graph must have at least one node");
        }

        Set<Integer> validatedNodeIds = validateNodes(rfg.getNodes());

        validateEdges(rfg.getEdges(),validatedNodeIds);
        checkForIsolatedNodes(rfg);

    }


    private static Set<Integer> validateNodes(List<ReactFlowNode> nodes){
        Set<Integer> nodeIds = new HashSet<>();
        Integer startingStateCount = 0;

        for(ReactFlowNode node : nodes){
            if(node == null){
                throw new InvalidReactFlowGraphException("Node cannot be null");
            }
            if(node.getId() == null){
                throw new InvalidReactFlowGraphException("Node ID cannot be null");
            }
            if(node.getId().isBlank()){
                throw new InvalidReactFlowGraphException("Node ID cannot be blank");
            }
            if(node.getData() == null){
                throw new InvalidReactFlowGraphException("Node data cannot be null");
            }
            if(node.getData().getLabel() == null){
                throw new InvalidReactFlowGraphException("Node: " + node.getId() + "label is null");
            }
            if(node.getData().getLabel().isBlank()){
                throw new InvalidReactFlowGraphException("Node: " + node.getId() + "label is blank");
            }

            Integer id = getIdInt(node.getId());
            
            if(nodeIds.contains(id)){
                throw new InvalidReactFlowGraphException("React Flow Graph cannot contain duplicates nodes");
            }

            if(node.getData().isStartingState()){
                startingStateCount++;
            }
            nodeIds.add(id);
        }
        if(startingStateCount != 1){
            throw new InvalidReactFlowGraphException("React Flow Graph must have exactly 1 start state");
        }
        return nodeIds;
    }

    private static void validateEdges(List<ReactFlowEdge> edges, Set<Integer>validatedNodes){
        if(edges == null){
                throw new InvalidReactFlowGraphException("React Flow Graph edges cannot be null");
        }
        
        Set<String> edgeIds = new HashSet<>();
        List<String> edgeLabels = new ArrayList<>();

        for(ReactFlowEdge edge: edges){
            if(edge == null){
                throw new InvalidReactFlowGraphException("Edge cannot be null");
            }
            if(edge.getId() == null){
                throw new InvalidReactFlowGraphException("Edge ID cannot be null");
            }
            if(edge.getId().isBlank()){
                throw new InvalidReactFlowGraphException("Edge ID cannot be blank");
            }
            if(edgeIds.contains(edge.getId())){
                throw new InvalidReactFlowGraphException("React Flow Graph cannot contain duplicate edges");
            }
            if(edge.getSource()== null){
                throw new InvalidReactFlowGraphException("Source ID cannot be null");
            }
            if(edge.getSource().isBlank()){
                throw new InvalidReactFlowGraphException("Source ID cannot be blank"); 
            }
            if(edge.getTarget()== null){
                throw new InvalidReactFlowGraphException("Target ID cannot be null"); 
            }
            if(edge.getTarget().isBlank()){
                throw new InvalidReactFlowGraphException("Target ID cannot be blank");
            }
            Integer source = getIdInt(edge.getSource());
            Integer target = getIdInt(edge.getTarget());

            if(!validatedNodes.contains(source)){
                throw new InvalidReactFlowGraphException("Source ID not a node in the React Flow Graph nodes");
            }
            if(!validatedNodes.contains(target)){
                throw new InvalidReactFlowGraphException("Target ID not a node in the React Flow Graph nodes");
            }
            edgeLabels.add(edge.getLabel());
            edgeIds.add(edge.getId());
        }
        validateEdgeLabels(edgeLabels);
    }

    private static void validateEdgeLabels(List<String> labels){
        if(labels == null){
                throw new InvalidReactFlowGraphException("Labels cannot be null");
        }
        for(String label: labels){
            validateEdgeLabelString(label);
        }
        

    }

    private static void validateEdgeLabelString(String label){
        if(label == null){
                throw new InvalidReactFlowGraphException("edge label cannot be null");
        }

        label = label.replaceAll("\\s+","");

        if(label.isBlank()){
            throw new InvalidReactFlowGraphException("edge label cannot be blank");
        }
        
        String[] splitLabelArray = label.split(",");

        for(String symbol: splitLabelArray){
            if(symbol == null){
                throw new InvalidReactFlowGraphException("Invalid label: symbol cannot be null");
            }
            if(symbol.isBlank()){
                throw new InvalidReactFlowGraphException("Invalid label: symbol cannot be blank");
            }
            if(symbol.length() != 1){
                throw new InvalidReactFlowGraphException("Invalid label: each symbol must only be 1 character");
            }
            char c = symbol.charAt(0);
            if(c == EPSILON){
                continue;
            }
            if(!String.valueOf(c).matches("^[A-Za-z0-9]$")){
                throw new InvalidReactFlowGraphException("Invalid label: invalid symbol");
            }  
        }
    }

    public static Integer getIdInt(String id){
        if(id== null){
                throw new InvalidReactFlowGraphException("ID cannot be null");
        }

        try{
            Integer idInt = Integer.parseInt(id);
            if(idInt < 0){
                throw new InvalidReactFlowGraphException("Node ID must be a positive integer");
            }
            return idInt;
        }catch(NumberFormatException e){
            throw new InvalidReactFlowGraphException("Invalid Node ID: " + id);
        }
    }

    private static void checkForIsolatedNodes(ReactFlowGraph rfg){
        Set<Integer> nodes = rfg.getNodes().stream().map(r->getIdInt(r.getId())).collect(Collectors.toSet());
        if(nodes.size()==1){
            return;
        }
        Set<Integer> sourceNodes = rfg.getEdges().stream().map(r->getIdInt(r.getSource())).collect(Collectors.toSet());
        Set<Integer> targetNodes = rfg.getEdges().stream().map(r->getIdInt(r.getTarget())).collect(Collectors.toSet());

        for(Integer node: nodes){
            if(!sourceNodes.contains(node) && !targetNodes.contains(node)){
                throw new InvalidReactFlowGraphException("Invalid React Flow Graph: cannot contain isolated node: " + node);
            }
        }
    }
    
}
