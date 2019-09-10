package com.ams.androiddevkit.baseClasses.globalKeys;

/**
 * Created by Ahmad on 23-Nov-16.
 */

@SuppressWarnings("unused")
public class BasePatterns {

    // Must match
    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@ [A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    // Must not match
    public static final String SPECIAL_CHARACTERS_PATTERN = "[!#$%^&*()-+=\\/;:\"',><?]";
    //
    public static String getOccurrenceOfCharacterPattern(String character) {
        return "(" + character + ")\1*\b";
    }
    // Must match (for the same splitting character)
    // One of the following characters : or . or - or space
    public static String get24TimeFormatPattern (String splittingCharacter) {
        return "^([0-1]?[0-9]|[2][0-3])" + splittingCharacter + "([0-5][0-9])(:[0-5][0-9])?$";
    }




}
