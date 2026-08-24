package com.jakeatkins.automataappbackend.dto;

/**
 * 
 * RegexWordTest
 * 
 * DTO containing a user-inputted regex string and a word to test on that string
 * 
 * 
 * @param word string of user-inputted word to test
 * @param regex string of user-inputted regex
 */


public record RegexWordTest(String word, String regex) {}
