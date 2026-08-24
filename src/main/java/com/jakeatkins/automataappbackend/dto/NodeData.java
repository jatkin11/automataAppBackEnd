package com.jakeatkins.automataappbackend.dto;

/**
 * 
 * NodeData
 * 
 * DTO containing the custom data required for Automata represention by a ReactFlowNode
 * 
 * NodeData is encapsulated within each node
 * 
 * @param label string of front-end node display label
 * @param startingState boolean of node being a starting state
 * @param acceptingState boolean of node being an accepting state
 */ 

public record NodeData (String label, boolean startingState, boolean acceptingState){}
