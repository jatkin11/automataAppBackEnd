package com.jakeatkins.automataappbackend.dto;

/**
 * 
 * ReactFlowEdge
 * 
 * DTO for an Edge within a ReactFlowGraph
 * 
 * Represents a transition between two states e.g. 0 - 'a' - > 1
 * 
 * @param id Edge ID
 * @param source Node ID of source state
 * @param target Node ID of target state
 * @param label Transition label of edge
 * @param type Custom edge type used for front-end processing
 */



public record ReactFlowEdge (String id, String source, String target, String label, String type){}
