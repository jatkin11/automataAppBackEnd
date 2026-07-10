package com.jakeatkins.automataappbackend.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jakeatkins.automataappbackend.dto.AutomataWordTest;
import com.jakeatkins.automataappbackend.dto.ReactFlowGraph;
import com.jakeatkins.automataappbackend.dto.RegexString;
import com.jakeatkins.automataappbackend.dto.RegexWordTest;
import com.jakeatkins.automataappbackend.dto.WordTestResponse;
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
    public ReactFlowGraph convertToNfa(@RequestBody RegexString regexInput) {
        return automataService.convertToNfa(regexInput);
    }

    @PostMapping("/test-word-on-automata")
    public WordTestResponse testWordOnAutomata(@RequestBody AutomataWordTest awt){
        return automataService.testWordOnAutomata(awt);
    }

    @PostMapping("/test-word-on-regex-string")
    public WordTestResponse testWordOnRegexString(@RequestBody RegexWordTest rwt){
        return automataService.testWordOnRegexString(rwt);
    }

    @PostMapping("/convert-to-regex-string")
    public RegexString convertToRegexString(@RequestBody ReactFlowGraph graph){
        return automataService.convertToRegexString(graph);
    }


}
