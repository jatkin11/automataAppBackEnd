package com.jakeatkins.automataappbackend.regex;

public class RegexStarred implements RegexToken {
    private final RegexToken starredRegex;

    public RegexStarred(RegexToken starredRegex){
        this.starredRegex = starredRegex;
    }

    public RegexToken getStarredRegex(){
        return starredRegex;
    }
}
