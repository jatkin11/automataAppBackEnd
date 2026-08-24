package com.jakeatkins.automataappbackend.regex;

/**
 * 
 * RegexToken
 * 
 * Shared interface used by all the concrete regex token types. e.g. RegexSymbol etc.
 * 
 * Represents a token from a parsed regex e.g. 'a' or 'a|b'. A parsed regex will ultimatley form a RegexToken Tree with nested RegexToken.
 * 
 */

public interface RegexToken {}
