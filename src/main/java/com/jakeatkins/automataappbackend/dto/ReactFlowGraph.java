package com.jakeatkins.automataappbackend.dto;

import java.util.List;

public class ReactFlowGraph {
    private String automataType;
    private List<ReactFlowEdge> edges;
    private List<ReactFlowNode> nodes;

    public ReactFlowGraph(){}

    public ReactFlowGraph(String automataType, List<ReactFlowEdge> edges, List<ReactFlowNode> nodes){
        this.automataType = automataType;
        this.edges = edges;
        this.nodes = nodes;
    }

    public String getAutomataType() {
        return automataType;
    }

    public List<ReactFlowEdge> getEdges() {
        return edges;
    }

    public List<ReactFlowNode> getNodes() {
        return nodes;
    }

    public void setAutomataType(String automataType) {
        this.automataType = automataType;
    }

    public void setEdges(List<ReactFlowEdge> edges) {
        this.edges = edges;
    }

    public void setNodes(List<ReactFlowNode> nodes) {
        this.nodes = nodes;
    }

}
