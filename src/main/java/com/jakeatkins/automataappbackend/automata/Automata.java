package com.jakeatkins.automataappbackend.automata;

import java.util.Map;
import java.util.Set;

public interface Automata {

        Set<Character> getAlphabet();

        Integer getStartState();

        Set<Integer> getAcceptingStates();

        Set<Integer> getStates();

        Map<Integer, Map<Character, Set<Integer>>> getTransitionMap();

        Map<Integer, String> getStateLabelMap();

}
