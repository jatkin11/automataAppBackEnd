package com.jakeatkins.automataappbackend.validators;

import com.jakeatkins.automataappbackend.dto.*;
import java.util.*;
import java.util.stream.Collectors;
import com.jakeatkins.automataappbackend.exceptions.*;

public class ReactFlowGraphValidator {
    
    //NEED TO CENTRALISE EPSILON
    private static final char EPSILON = 'ε';

    public static boolean validate(ReactFlowGraph rfg){
        return validateGraph(rfg);
    }

    public static boolean validateGraph(ReactFlowGraph rfg){
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

        return validateEdges(rfg.getEdges(),validatedNodeIds) && checkForIsolatedNodes(rfg);

    }


    public static Set<Integer> validateNodes(List<ReactFlowNode> nodes){
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

            Integer id = Integer.parseInt(node.getId());
            
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

    public static boolean validateEdges(List<ReactFlowEdge> edges, Set<Integer>validatedNodes){
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
                throw new InvalidReactFlowGraphException("React Flow Graph cannot contain duplicates nodes");
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
        return validateEdgeLabels(edgeLabels);
    }

    public static boolean validateEdgeLabels(List<String> labels){
        if(labels == null){
                throw new InvalidReactFlowGraphException("Labels cannot be null");
        }
        for(String label: labels){
            if(!validateEdgeLabelString(label)){
                throw new InvalidReactFlowGraphException("Invalid Label found");
            }
        }
        return true;
    }

    public static boolean validateEdgeLabelString(String label){
        if(label == null){
                throw new InvalidReactFlowGraphException("edge label cannot be null");
        }
        label = label.strip();
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
            if(!Character.isLetterOrDigit(c)){
                throw new InvalidReactFlowGraphException("Invalid label: invalid symbol");
            }  
        }
        return true;
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
            throw new InvalidReactFlowGraphException("Invalid Node ID: " + e);
        }
    }

    public static boolean checkForIsolatedNodes(ReactFlowGraph rfg){
        Set<Integer> nodes = rfg.getNodes().stream().map(r->getIdInt(r.getId())).collect(Collectors.toSet());
        if(nodes.size()==1){
            return true;
        }
        Set<Integer> sourceNodes = rfg.getEdges().stream().map(r->getIdInt(r.getSource())).collect(Collectors.toSet());
        Set<Integer> targetNodes = rfg.getEdges().stream().map(r->getIdInt(r.getTarget())).collect(Collectors.toSet());

        for(Integer node: nodes){
            if(!sourceNodes.contains(node) && !targetNodes.contains(node)){
                throw new InvalidReactFlowGraphException("Invalid React Flow Graph: cannot contain isolated node: " + node);
            }
        }

        return true;
    }
    

}
