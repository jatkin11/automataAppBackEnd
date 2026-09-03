package com.jakeatkins.automataappbackend.algos;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.jakeatkins.automataappbackend.automata.NFA;
import com.jakeatkins.automataappbackend.exceptions.InvalidRegexTokenException;
import com.jakeatkins.automataappbackend.regex.RegexConcat;
import com.jakeatkins.automataappbackend.regex.RegexEmptySet;
import com.jakeatkins.automataappbackend.regex.RegexEpsilon;
import com.jakeatkins.automataappbackend.regex.RegexStarred;
import com.jakeatkins.automataappbackend.regex.RegexSymbol;
import com.jakeatkins.automataappbackend.regex.RegexToken;
import com.jakeatkins.automataappbackend.regex.RegexUnion;
import com.jakeatkins.automataappbackend.validators.AutomataValidator;

/**
 * 
 * RegexToNfaConverter
 * 
 * Converts a RegexToken tree into an NFA
 * 
 * Adapted from the construction found in the report Chapter 4.2 (Allauzen and Mohri, 2006)
 *  
 */
public class RegexToNfaConverter {
    
    private record CurrentState(boolean nullType, Set<Integer> firstPositions, Set<Integer> lastPositions){};
    private final Map<Integer,Map<Character, Set<Integer>>> transitionMap = new HashMap<>();
    private final Map<Integer,Character> symbolPositionMap = new HashMap<>();
    private final static int NEW_START_STATE = 0;
    private final Map<Integer,String> stateLabelMap = new HashMap<>();
    private final RegexToken regex;
    
    /**
     * Constructor for RegexToNfaConverter instance
     * 
     * - Sets instance regex to the passed RegexToken
     * 
     * @param regex RegexToken tree to convert
     * @throws InvalidRegexTokenException if null
     */
    public RegexToNfaConverter(RegexToken regex){
        if(regex == null){
            throw new InvalidRegexTokenException("Regex token cannot be null");
        }
        this.regex = regex;
    }


    /**
     * 
     * @return
     */
    public NFA convert(){
        return glushkovConstruction(this.regex);

    }

    /**
     * 
     * @param regex
     * @return
     */
    private NFA glushkovConstruction(RegexToken regex){
        CurrentState cs = calculateCurrentState(regex);

        for(Integer x: cs.firstPositions()){
            addTransition(NEW_START_STATE, symbolPositionMap.get(x), x);
        }

        Set<Character> alphabet = new HashSet<>(symbolPositionMap.values());

        Set<Integer> states = new HashSet<>(symbolPositionMap.keySet());
        states.add(NEW_START_STATE);

        Set<Integer> acceptingStates = new HashSet<>(cs.lastPositions());

        if(cs.nullType()){
            acceptingStates.add(NEW_START_STATE);
        }

        generateStateLabelMap(states);

        NFA nfa = new NFA(NEW_START_STATE, states, acceptingStates, alphabet, this.transitionMap, this.stateLabelMap);
        AutomataValidator.validate(nfa);
        return nfa;       
    }


    /**
     * 
     * @param regex
     * @return
     */
    private CurrentState calculateCurrentState(RegexToken regex){
        if(regex == null){
            throw new InvalidRegexTokenException("Regex token tree cannot contain a null token");
        }

        if(regex instanceof RegexSymbol r){
            Integer position = r.position();
            if(position == NEW_START_STATE || position < 1){
                throw new InvalidRegexTokenException("Invalid Regex Tree: Invalid Position");
            }
            if(symbolPositionMap.containsKey(position)){
                throw new InvalidRegexTokenException("Invalid Regex Tree: duplicated positions found for symbol");
            }
            symbolPositionMap.put(position, r.symbol());
            return new CurrentState(false,Set.of(position),Set.of(position));
        }

        if(regex instanceof RegexEpsilon r){
            return new CurrentState(true, Collections.emptySet(), Collections.emptySet());
        }

        if(regex instanceof RegexEmptySet r){
            return new CurrentState(false, Collections.emptySet(), Collections.emptySet());
        }

        if(regex instanceof RegexUnion r){
            CurrentState left = calculateCurrentState(r.left());
            CurrentState right = calculateCurrentState(r.right());

            boolean nullType = left.nullType() || right.nullType();

            Set<Integer> firstPositions = Stream.concat(left.firstPositions().stream(), right.firstPositions().stream())
                                            .collect(Collectors.toSet());
            Set<Integer> lastPositions = Stream.concat(left.lastPositions().stream(), right.lastPositions().stream())
                                            .collect(Collectors.toSet());

            return new CurrentState(nullType, firstPositions, lastPositions);

        }

        if(regex instanceof RegexConcat r){
            CurrentState left = calculateCurrentState(r.left());
            CurrentState right = calculateCurrentState(r.right());

            boolean nullType = left.nullType() && right.nullType();

            Set<Integer> firstPositions;
            Set<Integer> lastPositions;

            if(left.nullType()){
                firstPositions = Stream.concat(left.firstPositions().stream(), right.firstPositions().stream())
                    .collect(Collectors.toSet());
            }else{
                firstPositions = left.firstPositions();
            }

            if(right.nullType()){
                lastPositions = Stream.concat(left.lastPositions().stream(), right.lastPositions().stream())
                    .collect(Collectors.toSet());
            }else{
                lastPositions = right.lastPositions();
            }

            for(Integer x : left.lastPositions()){
                for(Integer y : right.firstPositions()){
                    addTransition(x, symbolPositionMap.get(y), y);
                }
            }

            return new CurrentState(nullType, firstPositions, lastPositions);

        }

        if(regex instanceof RegexStarred r){
            CurrentState starred = calculateCurrentState(r.starredRegex());

            for(Integer x: starred.lastPositions()){
                for(Integer y: starred.firstPositions()){
                    addTransition(x, symbolPositionMap.get(y), y);
                }
            }

            return new CurrentState(true, starred.firstPositions(),starred.lastPositions());

        }

        throw new InvalidRegexTokenException("Invalid regex token tree");
    }


    /**
     * Adds transitions to instance transitionMap using pasesed source, symbol and target
     * 
     * @param source source state id
     * @param symbol transition symbol to add
     * @param target target state id
     */
    private void addTransition(Integer source, Character symbol, Integer target){
        this.transitionMap.computeIfAbsent(source,r -> new HashMap<>()).computeIfAbsent(symbol,r-> new HashSet<>()).add(target);
    }

    /**
     * Adds states and display labels to the stateLabelMap
     * 
     * e.g. state Id 0 would be added as <0,"q0">
     * @param states set of states to add to stateLabelMap
     */
    private void generateStateLabelMap(Set<Integer> states){
        for(Integer state : states){
            this.stateLabelMap.put(state,"q"+state);
        }
    }
}
