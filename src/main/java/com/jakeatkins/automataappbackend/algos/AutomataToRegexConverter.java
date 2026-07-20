package com.jakeatkins.automataappbackend.algos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.jakeatkins.automataappbackend.automata.Automata;
import com.jakeatkins.automataappbackend.regex.RegexConcat;
import com.jakeatkins.automataappbackend.regex.RegexEmptySet;
import com.jakeatkins.automataappbackend.regex.RegexEpsilon;
import com.jakeatkins.automataappbackend.regex.RegexStarred;
import com.jakeatkins.automataappbackend.regex.RegexSymbol;
import com.jakeatkins.automataappbackend.regex.RegexToken;
import com.jakeatkins.automataappbackend.regex.RegexUnion;
import com.jakeatkins.automataappbackend.validators.*;

public class AutomataToRegexConverter {
    
    private static final char EPSILON = 'ε';
    private static final int NEW_START_STATE = -1;
    private static final int NEW_FINAL_STATE = -2;
    private final Automata automata;
    private final Map<Integer,Map<Integer,RegexToken>> regexMap = new HashMap<>();

    public AutomataToRegexConverter(Automata automata){
        AutomataValidator.validate(automata);
        this.automata = automata;
    }

    //NEED TO ADD VALIDATION FOR THE AUTOMATA BEFORE CONVERTING
    //NEED TO ADD ERROR HANDLING
    //NEED TO MAKE THE NEW START STATE AND NEW FINAL STATE DYNAMIC, TO PREVENT ERROR IF A STATE OF THE AUTOMATA HAS THE SAME ID
    public RegexToken convert(){
        Set<Integer> rippableStates = new HashSet<>(this.automata.getStates());
        Set<Integer> allStates = new HashSet<>(rippableStates);
        
        allStates.add(NEW_START_STATE);
        allStates.add(NEW_FINAL_STATE);
        buildSourceToTargetRegexMap();

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
        return regexMap.get(NEW_START_STATE).get(NEW_FINAL_STATE);
    }

    public void ripState(int state){
        this.regexMap.remove(state);

        for(Map<Integer,RegexToken>innerMap: this.regexMap.values()){
            innerMap.remove(state);
        }

    }

    public void buildSourceToTargetRegexMap(){
        
        Set<Integer> allStates = new HashSet<>(this.automata.getStates());
        allStates.add(NEW_START_STATE);
        allStates.add(NEW_FINAL_STATE);

        for(Integer source: allStates){
            for(Integer target: allStates){
                addTransition(source, target, new RegexEmptySet());
            }
        }

        for(Integer acceptingState : this.automata.getAcceptingStates()){
            addTransition(acceptingState,NEW_FINAL_STATE, new RegexEpsilon());
        }

        addTransition(NEW_START_STATE, this.automata.getStartState(), new RegexEpsilon());

        for(Map.Entry<Integer,Map<Character,Set<Integer>>> sourceEntry : automata.getTransitionMap().entrySet()){

            Integer source = sourceEntry.getKey();

            for(Map.Entry<Character,Set<Integer>> symbolEntry: sourceEntry.getValue().entrySet()){

                char symbol = symbolEntry.getKey();

                for(int target : symbolEntry.getValue()){
                    if(symbol == EPSILON){
                        addTransition(source, target,new RegexEpsilon());
                    }else{
                        addTransition(source, target, new RegexSymbol(symbol,0)); //NEED TO GET RID OF THIS MAGIC NUMBER
                    }
                }
            }
        }
    }

    public void addTransition(int source, int target, RegexToken regex){
        RegexToken existingElseAddEmptySet = this.regexMap.computeIfAbsent(source, r-> new HashMap<>())
        .getOrDefault(target,new RegexEmptySet());

        this.regexMap.computeIfAbsent(source,r-> new HashMap<>())
        .put(target,union(existingElseAddEmptySet,regex));
    }

    public RegexToken union(RegexToken left, RegexToken right){
        if(left instanceof RegexEmptySet){
            return right;
        }
        if(right instanceof RegexEmptySet){
            return left;
        }
        if(left.equals(right)){ //NEED TO OVERIDE EQUALS IN THE REGEXUNION FOR THIS TO WORK OR CHANGE REGEXTOKEN IMPLEMENTAIONS TO RECORDS, TBD
            return left; 
        }
        return new RegexUnion(left,right);
    }

    public RegexToken concat(RegexToken left, RegexToken right){
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

    public RegexToken starred(RegexToken starred){
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
