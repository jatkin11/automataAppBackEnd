package com.jakeatkins.automataappbackend.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.jakeatkins.automataappbackend.exceptions.InvalidRegexException;

public class RegexValidatorTester {
    
    @Test
    void acceptsSingleSymbol(){
        assertEquals("a",RegexValidator.validate("a"));
    }

    @Test
    void acceptsDigit(){
        assertEquals("1",RegexValidator.validate("1"));
    }

    @Test
    void acceptsConcat(){
        assertEquals("ab",RegexValidator.validate("ab"));
    }

    @Test
    void acceptsUnion(){
        assertEquals("a|b",RegexValidator.validate("a|b"));
    }

    @Test
    void acceptsStarred(){
        assertEquals("a*",RegexValidator.validate("a*"));
    }

    @Test
    void acceptsBrackets(){
        assertEquals("(a)",RegexValidator.validate("(a)"));
    }

    @Test
    void acceptsGroupingOfConcat(){
        assertEquals("(ab)",RegexValidator.validate("(ab)"));
    }

    @Test
    void acceptsNestedGroups(){
        assertEquals("(a(ab))",RegexValidator.validate("(a(ab))"));
    }

    @Test
    void acceptsComplicatedRegex(){
        assertEquals("(a|b)*abb",RegexValidator.validate("(a|b)*abb"));

    }

    @Test
    void acceptsEpsilon(){
        assertEquals("ε",RegexValidator.validate("ε"));
    }

    @Test
    void acceptsEmptySet(){
        assertEquals("∅",RegexValidator.validate("∅"));
    }

    @Test
    void removesWhiteSpace(){
        assertEquals("(a|b)*abb",RegexValidator.validate(" (a |b) *ab b "));
    }

    @Test
    void rejectsNull(){
        assertThrows(InvalidRegexException.class,()-> RegexValidator.validate(null));
    }

    @Test
    void rejectsBlankRegex(){
        assertThrows(InvalidRegexException.class,()-> RegexValidator.validate(""));
    }

    @Test
    void rejectsIncorrectlyPlacedUnion(){
        assertThrows(InvalidRegexException.class,()-> RegexValidator.validate("a|"));
        assertThrows(InvalidRegexException.class,()-> RegexValidator.validate("|a"));
        assertThrows(InvalidRegexException.class,()-> RegexValidator.validate("a||a"));
        assertThrows(InvalidRegexException.class,()-> RegexValidator.validate("|a|a|"));
    }

    @Test
    void rejectsIncorrectlyPlacedStar(){
        assertThrows(InvalidRegexException.class,()-> RegexValidator.validate("a**"));
        assertThrows(InvalidRegexException.class,()-> RegexValidator.validate("*a"));
        assertThrows(InvalidRegexException.class,()-> RegexValidator.validate("*"));
        assertThrows(InvalidRegexException.class,()-> RegexValidator.validate("**a"));
    }

    @Test
    void rejectsIncorrectBrackets(){
        assertThrows(InvalidRegexException.class,()-> RegexValidator.validate("()"));
        assertThrows(InvalidRegexException.class,()-> RegexValidator.validate(")("));
        assertThrows(InvalidRegexException.class,()-> RegexValidator.validate("(ab))"));
        assertThrows(InvalidRegexException.class,()-> RegexValidator.validate("((ab)"));
    }

    @Test
    void rejectsInvalidChars(){
        assertThrows(InvalidRegexException.class,()-> RegexValidator.validate("="));
        assertThrows(InvalidRegexException.class,()-> RegexValidator.validate("%"));
        assertThrows(InvalidRegexException.class,()-> RegexValidator.validate("$"));
        assertThrows(InvalidRegexException.class,()-> RegexValidator.validate("-"));
    }

    @Test
    void rejectsInvalidInput(){
        assertThrows(InvalidRegexException.class,()-> RegexValidator.validate("ab(c)*d()"));
        assertThrows(InvalidRegexException.class,()-> RegexValidator.validate("a+b"));
        assertThrows(InvalidRegexException.class,()-> RegexValidator.validate("a c  d e ()"));
        assertThrows(InvalidRegexException.class,()-> RegexValidator.validate("*100*0120404*"));

    }

}
