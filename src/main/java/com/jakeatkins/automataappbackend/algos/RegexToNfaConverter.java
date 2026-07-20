package com.jakeatkins.automataappbackend.algos;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Objects;

import com.jakeatkins.automataappbackend.automata.NFA;
import com.jakeatkins.automataappbackend.regex.*;


public class RegexToNfaConverter {
    
    public record CurrentState(boolean nullType, Set<Integer> firstPositions, Set<Integer> lastPositions){};
    private final Map<Integer,Map<Character, Set<Integer>>> transitionMap = new HashMap<>();
    private final Map<Integer,Character> symbolPositionMap = new HashMap<>();
    private final static int NEW_START_STATE = 0;
    private final Map<Integer,String> stateLabelMap = new HashMap<>();
    private final RegexToken regex;
    
    public RegexToNfaConverter(RegexToken regex){
        this.regex = Objects.requireNonNull(regex);
    }

    public NFA convert(){
        return glushkovConstruction(this.regex);

    }
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

        return new NFA(NEW_START_STATE, states, acceptingStates, alphabet, this.transitionMap, this.stateLabelMap);
    }

    private CurrentState calculateCurrentState(RegexToken regex){
        
        if(regex instanceof RegexSymbol r){
            Integer position = r.position();
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

        throw new IllegalArgumentException("Invalid regex");
    }

    private void addTransition(Integer from, Character symbol, Integer to){
        this.transitionMap.computeIfAbsent(from,r -> new HashMap<>()).computeIfAbsent(symbol,r-> new HashSet<>()).add(to);
    }

    private void generateStateLabelMap(Set<Integer> states){
        for(Integer state : states){
            this.stateLabelMap.put(state,"q"+state);
        }
    }
}
