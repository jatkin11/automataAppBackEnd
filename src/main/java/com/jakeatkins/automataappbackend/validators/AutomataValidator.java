package com.jakeatkins.automataappbackend.validators;

import java.util.Map;
import java.util.Set;

import com.jakeatkins.automataappbackend.automata.Automata;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.EPSILON;
import com.jakeatkins.automataappbackend.automata.DFA;
import com.jakeatkins.automataappbackend.automata.NFA;
import com.jakeatkins.automataappbackend.exceptions.InvalidAutomataException;

public class AutomataValidator {
    
    public static void validate(Automata automata){
        validateStructure(automata);
        validateTransitions(automata);
    }

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

    private static boolean validateAlphabet(Set<Character> alphabet){
        for(Character c: alphabet){
            if(c == null || !validateSymbol(c)){
                return false;
            }
        }
        return true;
    }

    private static boolean validateSymbol(char c){
            return String.valueOf(c).matches("^[A-Za-z0-9]$");
    }

}
