package com.jakeatkins.automataappbackend.regex;

public record  RegexUnion (RegexToken left, RegexToken right) implements RegexToken{}
