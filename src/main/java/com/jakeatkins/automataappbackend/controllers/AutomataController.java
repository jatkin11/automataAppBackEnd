package com.jakeatkins.automataappbackend.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jakeatkins.automataappbackend.dto.ReactFlowGraph;
import com.jakeatkins.automataappbackend.services.AutomataService;

@RestController
@RequestMapping("/api/automata")
@CrossOrigin(origins = {"http://localhost:5173","https://automata.help"})
public class AutomataController {
    
    private final AutomataService automataService;

    public AutomataController(AutomataService automataService) {
        this.automataService = automataService;
    }

    @PostMapping("/convert-to-dfa")
    public ReactFlowGraph convertToDfa(@RequestBody ReactFlowGraph graph) {
        return automataService.convertToDfa(graph);
    }

    @PostMapping("/convert-to-nfa")
    public ReactFlowGraph convertToNfa(@RequestBody String regexInput) {
        return automataService.convertToNfa(regexInput);
    }


}
