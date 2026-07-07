package com.jakeatkins.automataappbackend.algos;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.jakeatkins.automataappbackend.automata.Automata;
import com.jakeatkins.automataappbackend.automata.NFA;
import com.jakeatkins.automataappbackend.regex.RegexToken;

public class WordTester {

//need to add better validation
public static boolean testAutomata(Automata automata, String word){
    if (automata == null || word == null){
        return false;
    }

    Map<Integer,Map<Character,Set<Integer>>> transitionMap = automata.getTransitionMap();

    Set<Integer> startStates = new HashSet<>();
    startStates.add(automata.getStartState());

    Set<Integer> states = new HashSet<>(EpsilonClosure.epsilonClosure(startStates, transitionMap));
    
    for(Character symbol : word.toCharArray()){
        Set<Integer> nextStates = new HashSet<>();

        for(Integer state: states){
            Set<Integer> reachableStates = automata.getTransitionMap().getOrDefault(state,Collections.emptyMap()).getOrDefault(symbol,Collections.emptySet());
            nextStates.addAll(reachableStates);
        }

        states = EpsilonClosure.epsilonClosure(nextStates, automata.getTransitionMap());
    }

    for(Integer state : states){
        if(automata.getAcceptingStates().contains(state)){
            return true;
        }
    }
    return false;
}

    //need to add better validation
    public static boolean testRegexString(String regex, String word){
        if(regex == null || word == null){
            return false;
        }
        RegexToken rt = new RegexTokeniser(regex).tokenise();
        NFA nfa = new RegexToNfaConverter(rt).convert();
        return testAutomata(nfa, word);
    }
    
}
