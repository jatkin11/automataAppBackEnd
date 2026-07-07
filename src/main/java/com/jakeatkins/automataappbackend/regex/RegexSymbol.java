package com.jakeatkins.automataappbackend.regex;

public class RegexSymbol implements RegexToken{
    private final char symbol;
    private final int glushkovPosition;

    public RegexSymbol(char symbol, int position){
        this.symbol = symbol;
        this.glushkovPosition = position;
    }

    public char getSymbol(){
        return symbol;
    }

    public int getGlushkovPosition(){
        return glushkovPosition;
    }

}
