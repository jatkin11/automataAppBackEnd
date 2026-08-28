package com.jakeatkins.automataappbackend.algos;

import com.jakeatkins.automataappbackend.automata.DFA;
import com.jakeatkins.automataappbackend.automata.NFA;
import com.jakeatkins.automataappbackend.validators.AutomataValidator;


/**
 * 
 * DfaMinimiser
 * 
 * 
 * minimises an automaton using Brzozowskis' algo
 * 
 */
public class DfaMinimiser {
    
  /**
   * NFA-to-minimised-DFA by:
   * - validating NFA
   * - converting NFA-to-DFA
   * - using brzozowskiAlgo
   * 
   * @param nfa NFA to convert into minimised DFA
   * @return minimised DFA
   */
    public static DFA minimise(NFA nfa){
      AutomataValidator.validate(nfa);
      DFA dfa = NfaToDfaConverter.convert(nfa);
      return brzozowskiAlgo(dfa);
    }


    /**
     * minimises DFA
     * 
     * Adapted from Brzozowski algorithm found in report chapter 4.4 (García, López and Vázquez de Parga, 2015)
     * 
     * - Reverses DFA -> Subset construction -> Reverses DFA -> subset construction
     * 
     * Uses DfaReverser and NfaToDfaConverter
     * 
     * @param dfa to minimise
     * @return minimised DFA
     */
    private static DFA brzozowskiAlgo(DFA dfa){
      NFA nfa1 = DfaReverser.reverse(dfa);
      DFA dfa1 = NfaToDfaConverter.convert(nfa1);
      NFA nfa2 = DfaReverser.reverse(dfa1);
      return NfaToDfaConverter.convert(nfa2);
    }

}
