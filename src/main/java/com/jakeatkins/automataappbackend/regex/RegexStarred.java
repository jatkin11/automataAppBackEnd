package com.jakeatkins.automataappbackend.regex;

/**
 * 
 * RegexStarred
 * 
 * Record of Kleene Star RegexToken
 * 
 * Implements RegexToken Interface
 * 
 * @param starredRegex contains the RegexToken to be starred
 * 
 * Represents a Kleene Star i.e. 'a*'' where 'a' is a RegexToken
 */

public record RegexStarred (RegexToken starredRegex)implements RegexToken{}
