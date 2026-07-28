package com.jakeatkins.automataappbackend.utilities;

import com.jakeatkins.automataappbackend.dto.RegexString;

public class TestRegexStringCreator {
    
    public static RegexString simpleValidRegexString(){
        return new RegexString("abc");
    }

     public static RegexString simpleInvalidRegexString(){
        return new RegexString("$£&");
    }

    public static RegexString RegexStringWithNullRegex(){
        return new RegexString(null);
    }

    public static RegexString RegexStringWithBlankRegex(){
        return new RegexString("");
    }

}
