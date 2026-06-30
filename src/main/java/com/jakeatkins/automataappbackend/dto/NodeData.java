package com.jakeatkins.automataappbackend.dto;


public class NodeData{
    private String label;
    private boolean startingState;
    private boolean acceptingState;

    public NodeData(){}

    public NodeData(String label, boolean startingState, boolean acceptingState){
        this.label = label;
        this.startingState = startingState;
        this.acceptingState = acceptingState;
    }

    public String getLabel() {
        return label;
    }

    public boolean isStartingState() {
        return startingState;
    }

    public boolean isAcceptingState() {
        return acceptingState;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setStartingState(boolean startingState) {
        this.startingState = startingState;
    }

    public void setAcceptingState(boolean acceptingState) {
        this.acceptingState = acceptingState;
    }

}