package com.jakeatkins.automataappbackend.automata;

import java.util.Map;
import java.util.Set;

/**
 * 
 * Automata
 * 
 * The shared interface implemented by concrete Automata i.e. NFA and DFA
 * 
 */

public interface Automata {

        /**
         * Returns Automata alphbet
         * 
         * @return set of alphabet symbols
         */
        Set<Character> alphabet();

        /**
         * Returns Automata starting state
         * 
         * @return starting state ID
         */
        Integer startState();

        /**
         * Returns Automtata accepting states
         * 
         * @return set of accepting state IDs
         */
        Set<Integer> acceptingStates();

        /**
         * Returns Automata states
         * 
         * @return set of automata state IDs
         */
        Set<Integer> states();

        /**
         * Returns Automata transitionMap
         * 
         * @return mapping of source state ID to target state ID via transition symbol character, e.g. <0,<'a',{1}>>
         */
        Map<Integer, Map<Character, Set<Integer>>> transitionMap();

        /**
         * Returns Automata stateLabelMap
         * @return mapping of state ID vs string of front-end display label e.g. <0,"q0">
         */
        Map<Integer, String> stateLabelMap();

        /**
         * Returns the display label for a passed state ID
         * 
         * @param state the state ID 
         * @return the display label
         */
        default String getLabel(Integer state){
                return stateLabelMap().get(state);
        };

}
