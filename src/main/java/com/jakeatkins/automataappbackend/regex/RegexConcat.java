package com.jakeatkins.automataappbackend.regex;

/**
 * 
 * RegexConcat
 * 
 * Record of Concatenation RegexToken
 * 
 * Implements RegexToken Interface
 * 
 * @param left contains the left regexToken of the concatenation of a regex
 * @param right contains the right regexToken of the concatenation of a regex
 */

public record RegexConcat (RegexToken left, RegexToken right)implements RegexToken{}
