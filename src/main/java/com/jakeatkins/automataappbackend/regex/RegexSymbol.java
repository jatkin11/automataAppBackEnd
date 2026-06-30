package com.jakeatkins.automataappbackend.regex;

public class RegexSymbol implements RegexToken{
    private final char symbol;

    public RegexSymbol(char symbol){
        this.symbol = symbol;
    }

    public char getSymbol(){
        return symbol;
    }

}
