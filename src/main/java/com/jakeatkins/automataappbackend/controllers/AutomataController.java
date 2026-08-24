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

/**
 * 
 * AutomataController
 * @RestController for API endpoints 
 * 
 * @RequestMapping provides root of endpoints
 * 
 * @CrossOrigin allows requests from:
 * - localHost: for use in offline testing
 * - Automata.help: for the hosted front-end
 * 
 * Each request and response is automatically de-serialised and re-serialised to/from JSON into relevant DTOs via Spring's Jackson
 *  
 */

@RestController
@RequestMapping("/api/automata")
@CrossOrigin(origins = {"http://localhost:5173","https://automata.help"})
public class AutomataController {
    
    private final AutomataService automataService;

    /**
     * Constructor for AutomataController
     * 
     * @param automataService passed AutomataService providing services
     */
    public AutomataController(AutomataService automataService) {
        this.automataService = automataService;
    }

    /**
     * NFA-to-DFA conversion
     *  
     * @param graph ReactFlowGraph of NFA
     * @return ReactFlowGraph of converted DFA
     */
    @PostMapping("/convert-to-dfa")
    public ReactFlowGraph convertToDfa(@RequestBody ReactFlowGraph graph) {
        return automataService.convertToDfa(graph);
    }

    /**
     * Regex-to-NFA conversion
     * 
     * @param regexInput RegexString of user-inputted regex
     * @return ReactFlowGraph of converted NFA
     */
    @PostMapping("/convert-to-nfa")
    public ReactFlowGraph convertToNfa(@RequestBody RegexString regexInput) {
        return automataService.convertToNfa(regexInput);
    }

    /**
     * Automata Word-Test
     * 
     * @param awt AutomataWordTest containing ReactFlowGraph and word to test
     * @return WordTestResposne containining test-result boolean
     */
    @PostMapping("/test-word-on-automata")
    public WordTestResponse testWordOnAutomata(@RequestBody AutomataWordTest awt){
        return automataService.testWordOnAutomata(awt);
    }

    /**
     * Regex Word-test
     * 
     * @param rwt RegexWordTest containing regex and word to test
     * @return WordTestResposne containining test-result boolean
     */
    @PostMapping("/test-word-on-regex-string")
    public WordTestResponse testWordOnRegexString(@RequestBody RegexWordTest rwt){
        return automataService.testWordOnRegexString(rwt);
    }

    /**
     * Automata-to-regex Conversion
     * @param graph ReactFlowGraph of Automaton to convert
     * @return RegexString of converted regex
     */
    @PostMapping("/convert-to-regex-string")
    public RegexString convertToRegexString(@RequestBody ReactFlowGraph graph){
        return automataService.convertToRegexString(graph);
    }

    /**
     * DFA minimisation
     * 
     * @param graph ReactFlowGraph of Automaton
     * @return ReactFlowGraph of miniimsed DFA
     */
    @PostMapping("/minimise-dfa")
    public ReactFlowGraph minimiseDfa(@RequestBody ReactFlowGraph graph){
        return automataService.minimiseDfa(graph);
    }

}
