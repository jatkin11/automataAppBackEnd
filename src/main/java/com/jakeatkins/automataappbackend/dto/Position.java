package com.jakeatkins.automataappbackend.dto;

/**
 * 
 * Position
 * 
 * X and Y co-ords required by a ReactFlowNode
 * 
 * Position is encapsulated within a ReactFlowNode
 *  
 * @param x int of x-ord
 * @param y int of y-ord
 */

public record Position (int x, int y){}
