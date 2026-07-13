package com.jakeatkins.automataappbackend.algos;

import com.jakeatkins.automataappbackend.automata.DFA;
import com.jakeatkins.automataappbackend.automata.NFA;

public class DfaMinimiser {
    
    public static DFA minimise(DFA dfa){
      return brozowskiAlgo(dfa);
    }

    public static DFA brozowskiAlgo(DFA dfa){
      NFA nfa1 = DfaReverser.reverse(dfa);
      DFA dfa1 = NfaToDfaConverter.convert(nfa1);
      NFA nfa2 = DfaReverser.reverse(dfa1);
      return NfaToDfaConverter.convert(nfa2);
    }

}
