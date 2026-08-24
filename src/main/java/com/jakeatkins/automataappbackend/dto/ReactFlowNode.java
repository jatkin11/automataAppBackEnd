package com.jakeatkins.automataappbackend.dto;

/**
 * 
 * ReactFlowNode
 * 
 * DTO of a Node within a ReactFlowGraph
 * 
 * Represents an Automaton State
 * 
 * @param id Node ID
 * @param position Encapsulated Position object contain x/y co-ords
 * @param data Enscapsulated NodeData object containing custom node info
 * @param type Custom Node type used for front-end processing
 */


public record ReactFlowNode (String id, Position position, NodeData data, String type) {}
