package com.jakeatkins.automataappbackend.dto;


public class ReactFlowNode {
    private String id;
    private Position position;
    private NodeData data;
    private String type;//required for front end custom nodes in react flow


    public ReactFlowNode(){}

    public ReactFlowNode(String id, Position position, NodeData data, String type){
        this.id = id;
        this.position = position;
        this.data = data;
        this.type = type;
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

    public String getType(){
        return this.type;
    }

    public void setType(String type){
        this.type = type;
    }

    
}
