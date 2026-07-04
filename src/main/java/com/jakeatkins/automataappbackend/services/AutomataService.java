package com.jakeatkins.automataappbackend.services;

import org.springframework.stereotype.Service;

import com.jakeatkins.automataappbackend.algos.NfaToDfaConverter;
import com.jakeatkins.automataappbackend.automata.DFA;
import com.jakeatkins.automataappbackend.automata.NFA;
import com.jakeatkins.automataappbackend.dto.ReactFlowGraph;
import com.jakeatkins.automataappbackend.mappers.AutomataToReactFlowGraphMapper;
import com.jakeatkins.automataappbackend.mappers.ReactFlowGraphToAutomataMapper;

@Service
public class AutomataService {
    
    public ReactFlowGraph convertToDfa(ReactFlowGraph graph) {
        NFA nfa = ReactFlowGraphToAutomataMapper.reactFlowGraphToNFA(graph);
        DFA dfa = NfaToDfaConverter.convert(nfa);
        return AutomataToReactFlowGraphMapper.fromAutomata(dfa);
    }

 

}
