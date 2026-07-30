package com.jakeatkins.automataappbackend.dto;

import java.util.List;

public record ReactFlowGraph (String automataType, List<ReactFlowEdge> edges, List<ReactFlowNode> nodes) {}
