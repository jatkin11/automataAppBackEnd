package com.jakeatkins.automataappbackend.dto;

import java.util.List;

/**
 * 
 * ReactFlowGraph
 * 
 * DTO of Automaton as React Flow Graph
 * 
 * @param automataType Automata type used for front-end processing
 * @param edges list of edges of an Automaton
 * @param nodes list of edges of an Automaton
 */

public record ReactFlowGraph (String automataType, List<ReactFlowEdge> edges, List<ReactFlowNode> nodes) {}
