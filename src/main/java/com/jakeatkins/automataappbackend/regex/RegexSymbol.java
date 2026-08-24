package com.jakeatkins.automataappbackend.regex;


/**
 *  
 * RegexSymbol
 * 
 * Record of a transition symbol RegexToken
 * 
 * @param symbol is the transtion symbol
 * @param position is the transition symbols Glushkov position within a regex, e.g a regex of 'aba' would have positions {a,1}, {b,2}, {a,3}.
 * 
 * Represents a transition symbol e.g. 'a'
 * 
 */

public record  RegexSymbol (char symbol, int position)implements RegexToken{}
