package com.jakeatkins.automataappbackend.algos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.jakeatkins.automataappbackend.automata.Automata;
import com.jakeatkins.automataappbackend.regex.*;
import com.jakeatkins.automataappbackend.validators.*;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.EPSILON;

public class AutomataToRegexConverter {
    
    private static final int unassignedRegexPosition = -1;
    private final int newStartState;
    private final int newFinalState;
    private final Automata automata;
    private final Map<Integer,Map<Integer,RegexToken>> regexMap = new HashMap<>();

    public AutomataToRegexConverter(Automata automata){
        AutomataValidator.validate(automata);
        Set<Integer> currentStates = new HashSet<>(automata.getStates());
        this.newStartState = UniqueStateGenerator.generate(currentStates);
        currentStates.add(this.newStartState);
        this.newFinalState = UniqueStateGenerator.generate(currentStates);
        this.automata = automata;
    }

    public RegexToken convert(){
        Set<Integer> rippableStates = new HashSet<>(this.automata.getStates());
        Set<Integer> allStates = new HashSet<>(rippableStates);
        
        allStates.add(newStartState);
        allStates.add(newFinalState);
        
        buildSourceToTargetRegexMap(allStates);

        for(Integer stateToRip : rippableStates){

            for(Integer source : allStates){
                if(source.equals(stateToRip)){continue;}

                for(Integer target: allStates){

                    if(target.equals(stateToRip)){continue;}

                    RegexToken builtRegex = regexMap.get(source).get(target);
                    RegexToken inboundRegex = regexMap.get(source).get(stateToRip);
                    RegexToken selfLoopRegex = regexMap.get(stateToRip).get(stateToRip);
                    RegexToken outboundRegex = regexMap.get(stateToRip).get(target);

                    RegexToken rippedRegex = concat(inboundRegex, concat(starred(selfLoopRegex), outboundRegex));

                    regexMap.get(source).put(target,union(builtRegex,rippedRegex));

                }
            }
            allStates.remove(stateToRip);
            ripState(stateToRip);


        }
        return regexMap.get(newStartState).get(newFinalState);
    }

    private void ripState(int state){
        this.regexMap.remove(state);

        for(Map<Integer,RegexToken>innerMap: this.regexMap.values()){
            innerMap.remove(state);
        }

    }

    private void buildSourceToTargetRegexMap(Set<Integer> allStates){
        
        for(Integer source: allStates){
            for(Integer target: allStates){
                addTransition(source, target, new RegexEmptySet());
            }
        }

        for(Integer acceptingState : this.automata.getAcceptingStates()){
            addTransition(acceptingState,this.newFinalState, new RegexEpsilon());
        }

        addTransition(this.newStartState, this.automata.getStartState(), new RegexEpsilon());

        for(Map.Entry<Integer,Map<Character,Set<Integer>>> sourceEntry : automata.getTransitionMap().entrySet()){

            Integer source = sourceEntry.getKey();

            for(Map.Entry<Character,Set<Integer>> symbolEntry: sourceEntry.getValue().entrySet()){

                char symbol = symbolEntry.getKey();

                for(int target : symbolEntry.getValue()){
                    if(symbol == EPSILON){
                        addTransition(source, target,new RegexEpsilon());
                    }else{
                        addTransition(source, target, new RegexSymbol(symbol,unassignedRegexPosition)); 
                    }
                }
            }
        }
    }

    private void addTransition(int source, int target, RegexToken regex){
        RegexToken existingElseAddEmptySet = this.regexMap.computeIfAbsent(source, r-> new HashMap<>())
        .getOrDefault(target,new RegexEmptySet());

        this.regexMap.computeIfAbsent(source,r-> new HashMap<>())
        .put(target,union(existingElseAddEmptySet,regex));
    }

    private RegexToken union(RegexToken left, RegexToken right){
        if(left instanceof RegexEmptySet){
            return right;
        }
        if(right instanceof RegexEmptySet){
            return left;
        }
        if(left.equals(right)){ 
            return left; 
        }
        return new RegexUnion(left,right);
    }

    private RegexToken concat(RegexToken left, RegexToken right){
        if(left instanceof RegexEpsilon){
            return right;
        }   
        if(right instanceof RegexEpsilon){
            return left;
        }
        if(left instanceof RegexEmptySet){
            return left;        
        }
        if(right instanceof RegexEmptySet){
            return right;
        }
        return new RegexConcat(left,right);
    }

    private RegexToken starred(RegexToken starred){
        if(starred instanceof RegexEmptySet){
            return new RegexEpsilon();
        }
        if(starred instanceof RegexEpsilon){
            return new RegexEpsilon();
        }
        if(starred instanceof RegexStarred){
            return starred;
        }
        return new RegexStarred(starred);
    }
}
