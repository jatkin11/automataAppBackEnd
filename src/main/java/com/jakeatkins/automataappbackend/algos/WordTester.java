package com.jakeatkins.automataappbackend.algos;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.jakeatkins.automataappbackend.automata.Automata;
import com.jakeatkins.automataappbackend.automata.NFA;
import com.jakeatkins.automataappbackend.exceptions.InvalidRegexException;
import com.jakeatkins.automataappbackend.exceptions.InvalidWordTestException;
import com.jakeatkins.automataappbackend.regex.RegexToken;
import com.jakeatkins.automataappbackend.validators.RegexStringValidator;
import com.jakeatkins.automataappbackend.validators.WordValidator;

public class WordTester {

//need to add better validation
public static boolean testAutomata(Automata automata, String word){
    if (automata == null){
        throw new InvalidWordTestException("Automata cannot be null");
    }

    if(word == null){
        throw new InvalidWordTestException("Word cannot be null");
    }

    if(!WordValidator.validateWord(word)){
        throw new InvalidWordTestException("Invalid word, must only contain letters or digits");
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
        if(regex == null){
            throw new InvalidRegexException("Regex cannot be null");
        }

        if(word == null){
            throw new InvalidWordTestException("Word cannot be null");
        }

        if(!RegexStringValidator.validateRegexString(regex)){
            throw new InvalidRegexException("Regex is invalid");
        }

        if(!WordValidator.validateWord(word)){
            throw new InvalidWordTestException("Invalid word, must only contain letters or digits");
        }

        RegexToken rt = new RegexTokeniser(regex).tokenise();
        NFA nfa = new RegexToNfaConverter(rt).convert();
        return testAutomata(nfa, word);
    }
    
}
