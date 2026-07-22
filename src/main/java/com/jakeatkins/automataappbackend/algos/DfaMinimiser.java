package com.jakeatkins.automataappbackend.algos;

import com.jakeatkins.automataappbackend.automata.DFA;
import com.jakeatkins.automataappbackend.automata.NFA;
import com.jakeatkins.automataappbackend.validators.*;

public class DfaMinimiser {
    
    public static DFA minimise(NFA nfa){
      AutomataValidator.validate(nfa);
      DFA dfa = NfaToDfaConverter.convert(nfa);
      return brzozowskiAlgo(dfa);
    }

    private static DFA brzozowskiAlgo(DFA dfa){
      NFA nfa1 = DfaReverser.reverse(dfa);
      DFA dfa1 = NfaToDfaConverter.convert(nfa1);
      NFA nfa2 = DfaReverser.reverse(dfa1);
      return NfaToDfaConverter.convert(nfa2);
    }

}
