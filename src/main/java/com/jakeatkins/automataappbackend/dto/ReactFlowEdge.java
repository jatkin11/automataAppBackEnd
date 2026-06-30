package com.jakeatkins.automataappbackend.dto;

public class ReactFlowEdge {
    
    private String id;
    private String type;
    private String source;
    private String target;
    private String label;

    public ReactFlowEdge(){}

    public ReactFlowEdge(String id, String source, String target, String label, String type){
        this.id = id;
        this.source = source;
        this.target = target;
        this.label = label;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public String getLabel() {
        return label;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
}
