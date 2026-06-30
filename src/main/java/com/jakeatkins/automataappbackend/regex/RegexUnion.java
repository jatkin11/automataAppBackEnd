package com.jakeatkins.automataappbackend.regex;

public class RegexUnion implements RegexToken{
    private final RegexToken left;
    private final RegexToken right;

    public RegexUnion(RegexToken left, RegexToken right){
        this.left = left;
        this.right = right;
    }

    public RegexToken getLeft(){
        return left;
    }

    public RegexToken getRight(){
        return right;
    }
}
