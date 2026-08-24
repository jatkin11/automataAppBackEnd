package com.jakeatkins.automataappbackend.regex;

/**
 * 
 * RegexUnion
 * 
 * Record of Union RegexToken
 * 
 * Implements RegexToken Interface
 * 
 * @param left contains the left regexToken of the union of a regex
 * @param right contains the right regexToken of the union of a regex
 * 
 * Represents a union in a regex e.g. 'a|b' where 'a' and 'b' are RegexSymbols
 * 
 */

public record  RegexUnion (RegexToken left, RegexToken right) implements RegexToken{}
