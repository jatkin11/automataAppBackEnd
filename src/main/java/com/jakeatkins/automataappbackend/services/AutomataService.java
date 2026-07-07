package com.jakeatkins.automataappbackend.services;

import org.springframework.stereotype.Service;

import com.jakeatkins.automataappbackend.algos.NfaToDfaConverter;
import com.jakeatkins.automataappbackend.algos.RegexToNfaConverter;
import com.jakeatkins.automataappbackend.algos.RegexTokeniser;
import com.jakeatkins.automataappbackend.automata.DFA;
import com.jakeatkins.automataappbackend.automata.NFA;
import com.jakeatkins.automataappbackend.dto.ReactFlowGraph;
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

    public ReactFlowGraph convertToNfa(String regexInput){
        RegexToken regex = new RegexTokeniser(regexInput).tokenise();
        NFA nfa = new RegexToNfaConverter(regex).convert();
        return AutomataToReactFlowGraphMapper.fromAutomata(nfa);
    }
 

}
