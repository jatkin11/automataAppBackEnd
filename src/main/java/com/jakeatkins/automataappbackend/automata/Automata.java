package com.jakeatkins.automataappbackend.automata;

import java.util.Map;
import java.util.Set;

public interface Automata<T> {

        Set<Character> getAlphabet();

        T getStartState();

        Set<T> getAcceptingStates();

        Set<T> getStates();

        Map<T, Map<Character, Set<T>>> getTransitionMap();

        Map<T, String> getStateLabelMap();

}
