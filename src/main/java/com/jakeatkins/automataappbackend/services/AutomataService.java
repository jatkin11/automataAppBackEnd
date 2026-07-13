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

@Service
public class AutomataService {
    
    public ReactFlowGraph convertToDfa(ReactFlowGraph graph) {
        NFA nfa = ReactFlowGraphToAutomataMapper.reactFlowGraphToNFA(graph);
        DFA dfa = NfaToDfaConverter.convert(nfa);
        return AutomataToReactFlowGraphMapper.fromAutomata(dfa);
    }

    public ReactFlowGraph convertToNfa(RegexString regexInput){
        RegexToken regex = new RegexTokeniser(regexInput.regex()).tokenise();
        NFA nfa = new RegexToNfaConverter(regex).convert();
        return AutomataToReactFlowGraphMapper.fromAutomata(nfa);
    }

    public WordTestResponse testWordOnAutomata(AutomataWordTest awt){
        ReactFlowGraph graph = awt.graph();
        NFA nfa = ReactFlowGraphToAutomataMapper.reactFlowGraphToNFA(graph);
        return new WordTestResponse(WordTester.testAutomata(nfa, awt.word()));
    }

    public WordTestResponse testWordOnRegexString(RegexWordTest rwt){
        return new WordTestResponse(WordTester.testRegexString(rwt.regex(), rwt.word()));
    }

    public RegexString convertToRegexString(ReactFlowGraph graph){
        NFA nfa = ReactFlowGraphToAutomataMapper.reactFlowGraphToNFA(graph);
        RegexToken regex = new AutomataToRegexConverter(nfa).convert();
        return new RegexString(RegexTokenToStringConverter.convert(regex));
    }

    public ReactFlowGraph minimiseDfa(ReactFlowGraph graph) {
        DFA dfa = ReactFlowGraphToAutomataMapper.reactFlowGraphToDfa(graph);
        DFA minimisedDFA = DfaMinimiser.minimise(dfa);
        return AutomataToReactFlowGraphMapper.fromAutomata(minimisedDFA);
    }
 }
