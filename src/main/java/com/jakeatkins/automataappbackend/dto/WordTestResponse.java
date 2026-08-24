package com.jakeatkins.automataappbackend.dto;

/**
 * 
 * WordTestResponse
 * 
 * DTO of word-testing response for either a RegexWordTest or AutomataWordTest
 * 
 * @param accepted boolean result of word-test
 */


public record  WordTestResponse(boolean accepted) {}
