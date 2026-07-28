package com.jakeatkins.automataappbackend.utilities;

import com.jakeatkins.automataappbackend.regex.RegexConcat;
import com.jakeatkins.automataappbackend.regex.RegexEmptySet;
import com.jakeatkins.automataappbackend.regex.RegexEpsilon;
import com.jakeatkins.automataappbackend.regex.RegexStarred;
import com.jakeatkins.automataappbackend.regex.RegexSymbol;
import com.jakeatkins.automataappbackend.regex.RegexToken;
import com.jakeatkins.automataappbackend.regex.RegexUnion;

public class TestRegexTokenCreator {
    

public static RegexToken RegexTokenOfSingleSymbolA(){
    return new RegexSymbol('A',1);
}


public static RegexToken RegexTokenOfConcatAandB(){
    return new RegexConcat(new RegexSymbol('A',1),new RegexSymbol('B',2));
}

public static RegexToken RegexTokenOfUnionOfAunionB(){
    return new RegexUnion(new RegexSymbol('A',1),new RegexSymbol('B',2));
}


public static RegexToken RegexTokenOfStarredA(){
    return new RegexStarred(new RegexSymbol('A',1));
}


public static RegexToken RegexTokenOfGroupedAConcatWithConcatBC(){
    return new RegexConcat(new RegexSymbol('A',1), new RegexConcat(new RegexSymbol('B',2),new RegexSymbol('C',3)));
}


public static RegexToken RegexTokenOfNestedAConcatBSurroundedByStar(){
    return new RegexStarred(new RegexConcat(new RegexSymbol('A',1),new RegexSymbol('B',2)));
}

public static RegexToken RegexTokenOfEmptySet(){
    return new RegexEmptySet();
}


public static RegexToken RegexTokenOfSingleEpsilon(){
    return new RegexEpsilon();
}

public static RegexToken RegexTokenStarOverConcatABSTAR(){
    return new RegexConcat(new RegexSymbol('A',1),new RegexStarred(new RegexSymbol('B',2)));
}

public static RegexToken RegexTokenConcatOverUnionABunionCD(){
    return new RegexUnion(new RegexConcat(new RegexSymbol('A',1),new RegexSymbol('B',2)), new RegexConcat(new RegexSymbol('C',3),new RegexSymbol('D',4)));
}

}
