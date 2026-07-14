package com.jakeatkins.automataappbackend.regex;

public record RegexConcat (RegexToken left, RegexToken right)implements RegexToken{}
