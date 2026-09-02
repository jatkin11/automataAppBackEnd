package com.jakeatkins.automataappbackend.algos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.jakeatkins.automataappbackend.automata.Automata;
import static com.jakeatkins.automataappbackend.automata.AutomataSymbols.EPSILON;
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
 * AutomataToRegexConverter
 * 
 * Converts Automata into a RegexToken tree
 * 
 */
public class AutomataToRegexConverter {
    
    private static final int unassignedRegexPosition = -1; // used for constructing RegexSymbol when not being used in Glushkov construction (regex position isn't required)
    private final int newStartState; // artificial start state
    private final int newFinalState; // artificial final state
    private final Automata automata; // automata to be converted 
    private final Map<Integer,Map<Integer,RegexToken>> regexMap = new HashMap<>(); // Map of Source to target states and their RegexToken e.g. <0, <1, RegexSymbol('a')>>

    /**
     * Constructor to AutomataToRegexConverter instance
     * 
     * - validates passed automata using AutomataValidator
     * - generates new artificial start state and stores it in newStartState
     * - generates new artificial final state and stores it in newFinalState
     * - sets instance automata to the passed automata
     * 
     * @param automata
     */
    public AutomataToRegexConverter(Automata automata){
        AutomataValidator.validate(automata);
        Set<Integer> currentStates = new HashSet<>(automata.states());
        this.newStartState = UniqueStateGenerator.generate(currentStates);
        currentStates.add(this.newStartState);
        this.newFinalState = UniqueStateGenerator.generate(currentStates);
        this.automata = automata;
    }


    /**
     * Converts Automata to RegexToken tree using the state elimination method
     * 
     * Adapted from the pseudocode found in Chapter 4.5  (Moreira, Nabais and Reis, 2010)
     * 
     * Process:
     * - rippable states = automata states (without new artificial start and final states)
     * - allStates = automata states + new artificial start and final states
     * - builds sourceToTarget regex Map for all state pairs i.e. similar to a 2D array of RegexTokens for every state with every other state
     * - for every state in rippableStates:
     *      - for every source state in allStates:
     *          - for every target state in allStates:
     *              - gets RegexToken from source-target pair from regexMap (i.e. existing regex from source to target)
     *              - gets RegexToken from source-to-'state-to-eliminate' pair from regexMap (i.e. inbound regex for state to be removed)
     *              - gets RegexToken from state-to-elminate to itself from regexMap (i.e. self-loop of state to be removed)
     *              - gets RegexToken from state-to eliminate to target pair from regexMap (i.e outbound regex for state to be removed)
     *              - creates a Regextoken of 'inbound + (self-loop)* + outbound' 
     *              - creates a union of the existing source-to-target regex with the new built regex and adds it to the regexMap for the source-to-target pair
     *      - removes state from regexMap
     * 
     * As a summary, goes through all non-artificial states, combines the inbound(from source), self-loop, 
     * and outbound(to target) regexes, and unionises it with the existing regex for source-target pair, then removes the state
     * 
     * @return RegexToken tree of final regex
     */
    public RegexToken convert(){
        Set<Integer> rippableStates = new HashSet<>(this.automata.states());
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

    /**
     *  Removes state from regexMap sources and targets
     * 
     * - removes state from regexMap source states
     * - removes state from regexMap target states
     * @param state  state to eliminate
     */
    private void ripState(int state){
        this.regexMap.remove(state);

        for(Map<Integer,RegexToken>innerMap: this.regexMap.values()){
            innerMap.remove(state);
        }

    }

    /**
     * Builds the initial source-to-target regexMap  e.g. <0,<1,RegexSymbol('a')>>
     * 
     * Process:
     * - Initially every state to every state is set to RegexEmptySet
     * - RegexEpsilon transitions are added from the original automata accepting states to the new final state
     * - A RegexEpsilon is added from the new starting state to the original starting state
     * - Converts every transition in the automata's transitionMap into a RegexToken and adds to the regexMap:
     *      - if the transition symbol is an Epsilon a RegexEpsilon is added, else
     *      - a RegexSymbol is added to the regexMap of the transition symbol e.g. RegexSymbol('a')
     * 
     * @param allStates all automata states (including new artificial start and final state)
     */
    private void buildSourceToTargetRegexMap(Set<Integer> allStates){
        
        for(Integer source: allStates){
            for(Integer target: allStates){
                addTransition(source, target, new RegexEmptySet());
            }
        }

        for(Integer acceptingState : this.automata.acceptingStates()){
            addTransition(acceptingState,this.newFinalState, new RegexEpsilon());
        }

        addTransition(this.newStartState, this.automata.startState(), new RegexEpsilon());

        for(Map.Entry<Integer,Map<Character,Set<Integer>>> sourceEntry : automata.transitionMap().entrySet()){

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

    /**
     * Adds a transition to the sourceToTarget RegexToken map
     * 
     * process:
     * - gets the existing RegexToken at the source and target location in the regexMap, else sets the 'existing' token as a RegexEmptySet
     * - calls union() on the generated 'existing' regex and the passed RegexToken and puts the returned RegexToken into the regexMap at source-target location
     * 
     * e.g if (0,1, RegexSymbol('a')) was passed, and there was no regex currently at location (0,1) of the regexMap, this function would add <0,<1,RegexUnion(RegexEmptySet,RegexSymbol('a'))>> (if no simplification was applied)
     * 
     * @param source source state ID
     * @param target target state ID
     * @param regex RegexToken to add to source-target transition
     */
    private void addTransition(int source, int target, RegexToken regex){
        RegexToken existingElseAddEmptySet = this.regexMap.computeIfAbsent(source, r-> new HashMap<>())
        .getOrDefault(target,new RegexEmptySet());

        this.regexMap.computeIfAbsent(source,r-> new HashMap<>())
        .put(target,union(existingElseAddEmptySet,regex));
    }

    /**
     * Creates RegexUnion of a passed left and right RegexToken, also provides RegexToken simplification rules
     * 
     * Simplifies:
     * ∅|X = X
     * X|∅ = X
     * X|X = X
     *  
     * @param left left RegexToken of union
     * @param right right RegexToken of union
     * @return RegexUnion of left and right RegexTokens (or other RegexToken based on simplification)
     */
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

    /**
     * Creates RegexConcat of a passed left and right RegexToken, also provides RegexToken simplification rules
     * 
     * Simplifies:
     * εX = X
     * Xε = X
     * X∅ = ∅
     * ∅X = ∅
     * 
     * @param left left RegexToken of concat
     * @param right right RegexToken of concat
     * @return RegexConcat of left and right RegexTokens (or other RegexToken based on simplification)
     */
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

    /**
     * 
     * Creates RegexStarred of a passed  RegexToken, also provides RegexToken simplification rules
     * 
     * Simplifies:
     * ∅* = ε
     * ε* = ε
     * X** = X*
     * @param starred RegexToken
     * @return RegexStarred of RegexToken (or other RegexToken based on simplification)
     */
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
