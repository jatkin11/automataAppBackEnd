package com.jakeatkins.automataappbackend.dto;


public class ReactFlowNode {
    private String id;
    private Position position;
    private NodeData data;


    public ReactFlowNode(){}

    public ReactFlowNode(String id, Position position, NodeData data){
        this.id = id;
        this.position = position;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public Position getPosition() {
        return position;
    }

    public NodeData getData() {
        return data;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setData(NodeData data) {
        this.data = data;
    }

    
}
