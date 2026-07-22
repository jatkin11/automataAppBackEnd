package com.jakeatkins.automataappbackend.validators;

import java.util.Set;
import java.util.Map;

import com.jakeatkins.automataappbackend.automata.*;
import com.jakeatkins.automataappbackend.exceptions.InvalidAutomataException;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.EPSILON;

public class AutomataValidator {
    
    public static void validate(Automata automata){
        validateStructure(automata);
        validateTransitions(automata);
    }

    public static void validateStructure(Automata automata){
        if(automata == null){
            throw new InvalidAutomataException("Automata cannot be null");
        }
        
        if(automata.getAcceptingStates() == null){
            throw new InvalidAutomataException("Automata accepting states cannot be null");
        }

        if(automata.getAlphabet() == null){
            throw new InvalidAutomataException("Automata alphabet cannot be null");
        }

        if(automata.getStartState() == null){
            throw new InvalidAutomataException("Automata start state cannot be null");
        }

        if(automata.getStates() == null){
            throw new InvalidAutomataException("Automata states cannot be null");
        }

        if(automata.getStateLabelMap() == null){
            throw new InvalidAutomataException("Automata state to label map cannot be null");
        }

        if(automata.getTransitionMap() == null){
            throw new InvalidAutomataException("Automata transition map cannot be null");
        }

        if(automata.getStates().size() < 1){
            throw new InvalidAutomataException("Automata must have at least one state");
        }

        if(!automata.getStates().containsAll(automata.getAcceptingStates())){
            throw new InvalidAutomataException("Automata states must include all accepting states");
        }

        if(!automata.getStates().contains(automata.getStartState())){
            throw new InvalidAutomataException("Automata states must include the starting state");
        }

        if(!validateAlphabet(automata.getAlphabet())){
            throw new InvalidAutomataException("Automata has invalid alphabet");
        }

        if(!automata.getStates().containsAll(automata.getStateLabelMap().keySet())){
            throw new InvalidAutomataException("State Label Map contains states not in Automata States");
        }

        if(!automata.getStateLabelMap().keySet().containsAll(automata.getStates())){
            throw new InvalidAutomataException("Missing states from state label map");
        }

    }

    public static void validateTransitions(Automata automata){
        for(Map.Entry<Integer,Map<Character,Set<Integer>>> transitions : automata.getTransitionMap().entrySet()){
            
            Integer source = transitions.getKey();

            if(source == null){
                throw new InvalidAutomataException("Transition source cannot be null");
            }

            if(!automata.getStates().contains(source)){
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
                    if(!automata.getAlphabet().contains(symbol)){
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

                    if(!automata.getStates().contains(target)){
                        throw new InvalidAutomataException("Automata states must contain all target states from transition map");
                    }
                }

            }
        }

    }

    public static boolean validateAlphabet(Set<Character> alphabet){
        for(Character c: alphabet){
            if(c == null || !validateSymbol(c)){
                return false;
            }
        }
        return true;
    }

    // NEED TO UPDATE THIS TO ONLY INCLUDE A-Z, a-z, 0-9 as currently accepts special letters
    public static boolean validateSymbol(char c){
            return Character.isLetterOrDigit(c);
    }

}
