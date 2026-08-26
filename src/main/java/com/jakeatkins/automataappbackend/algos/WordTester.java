package com.jakeatkins.automataappbackend.algos;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.jakeatkins.automataappbackend.automata.Automata;
import com.jakeatkins.automataappbackend.automata.NFA;
import com.jakeatkins.automataappbackend.regex.RegexToken;
import com.jakeatkins.automataappbackend.validators.AutomataValidator;
import com.jakeatkins.automataappbackend.validators.RegexValidator;
import com.jakeatkins.automataappbackend.validators.WordValidator;

/**
 * WordTester
 * 
 * Tests a user-inputted word on a rege x or automaon
 */
public class WordTester {

/** 
 * Tests user-inputted word on automaton
 * 
 * Goes through each symbol in a word and checks whats reachable by checking the transitionMap, 
 * and using whats reachable as the source state for the next symbol. If any final reachable state is accepting, then it returns true
 * 
 * Process:
 * - validates automata and word
 * - creates a new set of startStates and adds the starting state from passed automata
 * - gets the epsilon closure of that set of startStates - called 'states'
 * - then, for each symbol in the word:
 *          for each state in the 'states':
 *                adds any reachable state from that state on that symbol to a set of reachable states (using the transitionMap)
 *          states is updated to the epislon closure of the reachable states, the loop moves to next symbol
 * if any of the final reachable states are accepting states then it returns true, else false
 * 
 * 
 * @param automata automaton to test
 * @param word word to test
 * @return boolean test result
 */
public static boolean testAutomata(Automata automata, String word){

    AutomataValidator.validate(automata);
    WordValidator.validate(word);

    Map<Integer,Map<Character,Set<Integer>>> transitionMap = automata.transitionMap();

    Set<Integer> startStates = new HashSet<>();
    startStates.add(automata.startState());

    Set<Integer> states = new HashSet<>(EpsilonClosure.epsilonClosure(startStates, transitionMap));
    
    for(Character symbol : word.toCharArray()){
        Set<Integer> nextStates = new HashSet<>();

        for(Integer state: states){
            Set<Integer> reachableStates = transitionMap.getOrDefault(state,Collections.emptyMap()).getOrDefault(symbol,Collections.emptySet());
            nextStates.addAll(reachableStates);
        }

        states = EpsilonClosure.epsilonClosure(nextStates, transitionMap);
    }

    for(Integer state : states){
        if(automata.acceptingStates().contains(state)){
            return true;
        }
    }
    return false;
}

/**
 * Tests user-inputted word on regex string
 * 
 * Reuses RegexTokensier, RegexToNfaConverter and testAutomata function
 * 
 * Process:
 * - Validates regex and word
 * - tokenises regex
 * - converts regexToken into NFA
 * - uses testAutomata to test word on new NFA
 * @param regex regex string to test
 * @param word word to test
 * @return boolean test-result
 */
    public static boolean testRegexString(String regex, String word){
        RegexValidator.validate(regex);
        WordValidator.validate(word);

        RegexToken rt = new RegexTokeniser(regex).tokenise();
        NFA nfa = new RegexToNfaConverter(rt).convert();
        return testAutomata(nfa, word);
    }
    
}
