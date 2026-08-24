package com.jakeatkins.automataappbackend.services;

import org.springframework.stereotype.Service;

import com.jakeatkins.automataappbackend.algos.AutomataToRegexConverter;
import com.jakeatkins.automataappbackend.algos.DfaMinimiser;
import com.jakeatkins.automataappbackend.algos.NfaToDfaConverter;
import com.jakeatkins.automataappbackend.algos.RegexToNfaConverter;
import com.jakeatkins.automataappbackend.algos.RegexTokenToStringConverter;
import com.jakeatkins.automataappbackend.algos.RegexTokeniser;
import com.jakeatkins.automataappbackend.algos.WordTester;
import com.jakeatkins.automataappbackend.automata.DFA;
import com.jakeatkins.automataappbackend.automata.NFA;
import com.jakeatkins.automataappbackend.dto.AutomataWordTest;
import com.jakeatkins.automataappbackend.dto.ReactFlowGraph;
import com.jakeatkins.automataappbackend.dto.RegexString;
import com.jakeatkins.automataappbackend.dto.RegexWordTest;
import com.jakeatkins.automataappbackend.dto.WordTestResponse;
import com.jakeatkins.automataappbackend.mappers.AutomataToReactFlowGraphMapper;
import com.jakeatkins.automataappbackend.mappers.ReactFlowGraphToAutomataMapper;
import com.jakeatkins.automataappbackend.regex.RegexToken;
import com.jakeatkins.automataappbackend.validators.AutomataWordTestValidator;
import com.jakeatkins.automataappbackend.validators.ReactFlowGraphValidator;
import com.jakeatkins.automataappbackend.validators.RegexStringValidator;
import com.jakeatkins.automataappbackend.validators.RegexWordTestValidator;
import com.jakeatkins.automataappbackend.validators.WordValidator;

/**
 * 
 * AutomataService
 * 
 * Spring @Service class providing all conversion, minimisation and word-testing complete processes
 * 
 * Each process:
 * - receives DTO from the AutomataController
 * - validates DTO, 
 * - provides mapping from DTO, 
 * - performs the key process e.g. conversion,
 * - provides mapping to DTO,
 * - returns a DTO to the AutomataController
 */


@Service
public class AutomataService {
    
    /**
     * Conversion from NFA-to-DFA
     * 
     * @param graph ReactFlowGraph DTO of automata of NFA to be converted
     * @return ReactFlowGraph DTO of converted DFA
     */
    public ReactFlowGraph convertToDfa(ReactFlowGraph graph) {
        ReactFlowGraphValidator.validate(graph);
        NFA nfa = ReactFlowGraphToAutomataMapper.reactFlowGraphToNFA(graph);
        DFA dfa = NfaToDfaConverter.convert(nfa);
        return AutomataToReactFlowGraphMapper.fromAutomata(dfa);
    }

    /**
     * Conversion from Regex-to-Automata
     * 
     * @param regexInput RegexSTring DTO of user-inputted regex to be converted
     * @return ReactFlowGraph of converted NFA
     */
    public ReactFlowGraph convertToNfa(RegexString regexInput){
        RegexStringValidator.validate(regexInput);
        RegexToken regex = new RegexTokeniser(regexInput.regex()).tokenise();
        NFA nfa = new RegexToNfaConverter(regex).convert();
        return AutomataToReactFlowGraphMapper.fromAutomata(nfa);
    }

    /**
     * Word-testing on Automata
     * 
     * @param awt AutomataWordTest DTO containing a ReactFlowGraph of the Automata to test, and a word to test on that Automaton
     * @return a WordTestResponse DTO containing the boolean of the test-result
     */
    public WordTestResponse testWordOnAutomata(AutomataWordTest awt){
        AutomataWordTestValidator.validate(awt);
        ReactFlowGraph graph = awt.graph();
        String word = awt.word();
        ReactFlowGraphValidator.validate(graph);
        WordValidator.validate(word);
        NFA nfa = ReactFlowGraphToAutomataMapper.reactFlowGraphToNFA(graph);
        return new WordTestResponse(WordTester.testAutomata(nfa, word));
    }

    /**
     * Word-testing on Regex
     * 
     * @param rwt RegexWordTest DTO containing a regex to test, and a word to test on that regex
     * @return a WordTestResponse DTO containing the boolean of the test-result
     */
    public WordTestResponse testWordOnRegexString(RegexWordTest rwt){
        RegexWordTestValidator.validate(rwt);
        WordValidator.validate(rwt.word());
        return new WordTestResponse(WordTester.testRegexString(rwt.regex(), rwt.word()));
    }

    /**
     * Automata-to-regex conversion
     * 
     * @param graph ReactFlowGraph of Automaton to convert
     * @return RegexString response DTO containing converted regex string
     */
    public RegexString convertToRegexString(ReactFlowGraph graph){
        ReactFlowGraphValidator.validate(graph);
        NFA nfa = ReactFlowGraphToAutomataMapper.reactFlowGraphToNFA(graph);
        RegexToken regex = new AutomataToRegexConverter(nfa).convert();
        return new RegexString(RegexTokenToStringConverter.convert(regex));
    }

    /**
     * DFA Minismisation
     *  
     * @param graph ReactFlowGraph of an Automaton to minimise
     * @return ReactFlowGraph of the minimised DFA
     * 
     * Note: this accepts all automaton from the front-end (NFA or DFA). The result will always be a DFA.
     * The process converts all graphs into an NFA first before passing to the MinimiseDFA function in the DfaMinimser
     * This allows for all user-drawn valid automaton to be converted into a minimised DFA
     */
    public ReactFlowGraph minimiseDfa(ReactFlowGraph graph) {
        ReactFlowGraphValidator.validate(graph);
        NFA nfa = ReactFlowGraphToAutomataMapper.reactFlowGraphToNFA(graph);
        DFA minimisedDFA = DfaMinimiser.minimise(nfa);
        return AutomataToReactFlowGraphMapper.fromAutomata(minimisedDFA);
    }
 }
