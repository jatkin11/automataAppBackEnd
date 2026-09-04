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
    
    private record CurrentState(boolean nullType, Set<Integer> firstPositions, Set<Integer> lastPositions){}; // stores Glushkov properties(first positions, last positions, nullability)
    private final Map<Integer,Map<Character, Set<Integer>>> transitionMap = new HashMap<>(); // NFA transitionMap
    private final Map<Integer,Character> symbolPositionMap = new HashMap<>(); // maps regex symbol positions with its char
    private final static int NEW_START_STATE = 0; // artificial starting state
    private final Map<Integer,String> stateLabelMap = new HashMap<>(); // maps NFA state IDs with display labels
    private final RegexToken regex; // RegexToken to be converted
    
    /**
     * Constructor for RegexToNfaConverter instance
     * 
     * - checks passed RegexToken tree is not null
     * - Sets instance regex to the passed RegexToken
     * 
     * @param regex RegexToken tree to convert
     * @throws InvalidRegexTokenException if regex is null
     */
    public RegexToNfaConverter(RegexToken regex){
        if(regex == null){
            throw new InvalidRegexTokenException("Regex token cannot be null");
        }
        this.regex = regex;
    }


    /**
     * Converts the instance RegexToken tree into an NFA using the Glushkov construction
     * @return converted NFA
     */
    public NFA convert(){
        return glushkovConstruction(this.regex);

    }

    /**
     * The Glushkov construction algorithm that converts RegexToken tree into an NFA
     * 
     * Process:
     * - calculates CurrentState of passed RegexToken
     * - adds a transition from the new start state to all the first positions
     * - copies set of chars from symbolPositionMap to new NFA alphabet set
     * - creates NFA states from the  symbol positions and adds the artificial start state (total number of states for Glushkov construction = number of symbol positions + 1)
     * - sets the last positions of the CurrentState as accepting states
     * - if the current state is nullable, sets the new start state to an accepting state 
     * - generates display labels for the new states into the stateLabelMap
     * - constructs new NFA
     * - validates NFA
     * 
     * @param regex RegexToken tree to convert
     * @return constructed NFA
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
     * Calculates the Glushkov properties of a given RegexToken tree i.e. first positions, last positions, and nullability 
     * and adds follow transitions to the instance transitionMap
     * 
     * Process:
     * - recursively checks the concrete class of the passed RegexToken:
     *      - uses the construction rules from Chapter 4.2 of report for each RegexToken type
     *      - adds relevant follow transitions to the instance transitionMap (for concat and starred)
     *      - passes CurrentState up the recursion
     * - returns a CurrentState containing the overall regex first positions, last positions, and nullability 
     * 
     * @param regex RegexToken tree to calculate
     * @return CurrentState (first,last,nullability)
     * @throws InvalidRegexTokenException if RegexToken is invalid
     */
    private CurrentState calculateCurrentState(RegexToken regex){
        if(regex == null){
            throw new InvalidRegexTokenException("Regex token tree cannot contain a null token");
        }

        //nullable: false, first: position(r), last: position(r) 
        if(regex instanceof RegexSymbol r){
            Integer position = r.position();
            if(position == NEW_START_STATE || position < 1){
                throw new InvalidRegexTokenException("Invalid Regex Tree: Invalid Position");
            }
            if(symbolPositionMap.containsKey(position)){
                throw new InvalidRegexTokenException("Invalid Regex Tree: duplicated positions found for symbol");
            }
            //when a symbol is found, its position in the regex and its character is added to the symbolPositionMap
            symbolPositionMap.put(position, r.symbol());
            return new CurrentState(false,Set.of(position),Set.of(position));
        }

        //nullable: true, 
        // first: ∅, 
        // last: ∅
        if(regex instanceof RegexEpsilon r){
            return new CurrentState(true, Collections.emptySet(), Collections.emptySet());
        }

        //nullable: false, 
        // first: ∅, 
        // last: ∅
        if(regex instanceof RegexEmptySet r){
            return new CurrentState(false, Collections.emptySet(), Collections.emptySet());
        }

        //nullable: nullable(left) || nullable(right), 
        // first: first(left) U first(right), 
        // last: last(left) U last(right)
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

        //nullable: nullable(left) && nullable(right), 
        //first: if nullable(left) -> first(left) U first(right) : first(left), 
        //last: if nullable(right) -> last(left) U last(right) : last(right)
        //follow: last(left) -> first(right)
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

            // adds transitions to the transitionMap from last(left)->first(right)
            for(Integer x : left.lastPositions()){
                for(Integer y : right.firstPositions()){
                    addTransition(x, symbolPositionMap.get(y), y);
                }
            }

            return new CurrentState(nullType, firstPositions, lastPositions);

        }

        // nullable: true, 
        // first: first(r), 
        // last: last(r), 
        // follow: last(r)->first(r)
        if(regex instanceof RegexStarred r){
            CurrentState starred = calculateCurrentState(r.starredRegex());

            // adds transitions to the transitionMap from last(r)->first(r)
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
     * Adds transitions to instance transitionMap using passed source, symbol and target
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
