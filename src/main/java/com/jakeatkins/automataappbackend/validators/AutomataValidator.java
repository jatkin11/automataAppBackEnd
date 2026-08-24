package com.jakeatkins.automataappbackend.validators;

import java.util.Map;
import java.util.Set;

import com.jakeatkins.automataappbackend.automata.Automata;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.EPSILON;
import com.jakeatkins.automataappbackend.automata.DFA;
import com.jakeatkins.automataappbackend.automata.NFA;
import com.jakeatkins.automataappbackend.exceptions.InvalidAutomataException;

/**
 * 
 * AutomataValidator
 * 
 * Validation Class for checking the structure/transitions of an Automaton
 * 
 */

public class AutomataValidator {
    
    /**
     * Validates the structure and transitions of an automata using helper methods
     * 
     * @param automata the automata to be validated (NFA/DFA)
     * @throws InvalidAutomataException if fails any validation
     */
    public static void validate(Automata automata){
        validateStructure(automata);
        validateTransitions(automata);
    }


    /**
     * 
     * Validates the structre of the passed Automata
     * 
     * Checks:
     * - no null entries
     * - automaton has at least one state
     * - automaton states contains the starting state
     * - automaton states contains the accepting states
     * - automaton states contains all states in the stateLabelMap
     * - stateLabel map contains all states from the automaton states
     * 
     * @param automata the automaton to be validated
     * @throws InvalidAutomataException if fails any validation
     */
    private static void validateStructure(Automata automata){
        if(automata == null){
            throw new InvalidAutomataException("Automata cannot be null");
        }
        
        if(automata.acceptingStates() == null){
            throw new InvalidAutomataException("Automata accepting states cannot be null");
        }

        if(automata.alphabet() == null){
            throw new InvalidAutomataException("Automata alphabet cannot be null");
        }

        if(automata.startState() == null){
            throw new InvalidAutomataException("Automata start state cannot be null");
        }

        if(automata.states() == null){
            throw new InvalidAutomataException("Automata states cannot be null");
        }

        if(automata.stateLabelMap() == null){
            throw new InvalidAutomataException("Automata state to label map cannot be null");
        }

        if(automata.transitionMap() == null){
            throw new InvalidAutomataException("Automata transition map cannot be null");
        }

        if(automata.states().size() < 1){
            throw new InvalidAutomataException("Automata must have at least one state");
        }

        if(!automata.states().containsAll(automata.acceptingStates())){
            throw new InvalidAutomataException("Automata states must include all accepting states");
        }

        if(!automata.states().contains(automata.startState())){
            throw new InvalidAutomataException("Automata states must include the starting state");
        }

        if(!validateAlphabet(automata.alphabet())){
            throw new InvalidAutomataException("Automata has invalid alphabet");
        }

        if(!automata.states().containsAll(automata.stateLabelMap().keySet())){
            throw new InvalidAutomataException("State Label Map contains states not in Automata States");
        }

        if(!automata.stateLabelMap().keySet().containsAll(automata.states())){
            throw new InvalidAutomataException("Missing states from state label map");
        }

    }

    /**
     * Validates the transitions within an automaton
     * 
     * Checks the transitions sources, targets, and symbols
     * 
     * Cycles through the layers of the Automaton's transitionMap to check:
     * - no null entries
     * - source not null
     * - automata states contains the source state
     * - DFA do not contain any epsilon transitions
     * - automata states contains the target state
     * - DFA only have have one target state per transition
     * 
     * @param automata
     * @throws InvalidAutomataException if any of the checks fail
     * 
     */
    private static void validateTransitions(Automata automata){
        for(Map.Entry<Integer,Map<Character,Set<Integer>>> transitions : automata.transitionMap().entrySet()){
            
            Integer source = transitions.getKey();

            if(source == null){
                throw new InvalidAutomataException("Transition source cannot be null");
            }

            if(!automata.states().contains(source)){
                throw new InvalidAutomataException("Automata states must contain transition source state");
            }

            Map<Character,Set<Integer>> symbolAndTargetMapPerState = transitions.getValue();

            if(symbolAndTargetMapPerState == null){
                throw new InvalidAutomataException("State transition inner map cannot be null");
            }


            for (Map.Entry<Character,Set<Integer>> transition: symbolAndTargetMapPerState.entrySet()){
                Character symbol = transition.getKey();
                Set<Integer> targets = transition.getValue();

                if(symbol == null){
                    throw new InvalidAutomataException("Transition symbol cannot be null in transition map");
                }

                if(targets == null){
                    throw new InvalidAutomataException("Target states cannot be null in transition map");
                }

                if(symbol == EPSILON){
                    if(!(automata instanceof NFA)){
                        throw new InvalidAutomataException("DFA cannot contain EPSILON transitions");
                    }
                }else{
                    if(!validateSymbol(symbol)){
                        throw new InvalidAutomataException("Invalid symbol: " + symbol);
                    }
                    if(!automata.alphabet().contains(symbol)){
                    throw new InvalidAutomataException("Alpabet missing symbol: " + symbol);
                    }
                }

                if(targets.isEmpty()){
                    throw new InvalidAutomataException("transition cannot go to empty state");
                }

                if(automata instanceof DFA && targets.size() != 1){
                    throw new InvalidAutomataException("DFA must have only one target state per transition");
                }

                for(Integer target : targets){
                    if(target == null){
                        throw new InvalidAutomataException("target state cannot be null in transtion map");
                    }

                    if(!automata.states().contains(target)){
                        throw new InvalidAutomataException("Automata states must contain all target states from transition map");
                    }
                }

            }
        }

    }

    /**
     * Helper method to validate the whole alphabet of an Automaton
     * 
     * Takes the set of alphabet characters and validates each symbol
     * 
     * @param alphabet set of Automaton alphabets chars
     * @return true if all letters in the alphabet are valid, else false
     */
    private static boolean validateAlphabet(Set<Character> alphabet){
        for(Character c: alphabet){
            if(c == null || !validateSymbol(c)){
                return false;
            }
        }
        return true;
    }


    /**
     * Helper method for validation Symbol characters
     * 
     * @param c Char to be validated 
     * @return true if the char is A-Z, a-z, or 0-9, else false
     */
    private static boolean validateSymbol(char c){
            return String.valueOf(c).matches("^[A-Za-z0-9]$");
    }

}
