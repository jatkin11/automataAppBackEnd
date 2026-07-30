package com.jakeatkins.automataappbackend.automata;

import java.util.Map;
import java.util.Set;

public interface Automata {

        Set<Character> alphabet();

        Integer startState();

        Set<Integer> acceptingStates();

        Set<Integer> states();

        Map<Integer, Map<Character, Set<Integer>>> transitionMap();

        Map<Integer, String> stateLabelMap();

        default String getLabel(Integer state){
                return stateLabelMap().get(state);
        };

}
