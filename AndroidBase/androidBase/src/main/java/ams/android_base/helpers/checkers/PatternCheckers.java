package ams.android_base.helpers.checkers;

import static ams.android_base.baseClasses.globalKeys.BasePatterns.EMAIL_PATTERN;
import static ams.android_base.baseClasses.globalKeys.BasePatterns.SPECIAL_CHARACTERS_PATTERN;
import static ams.android_base.baseClasses.globalKeys.BasePatterns.get24TimeFormatPattern;

/**
 * Created by Ahmad Mahmoud on 22-Feb-18.
 */

@SuppressWarnings("unused")
public class PatternCheckers {
    public static boolean isValidMailPattern(String mail){
        return mail.matches(EMAIL_PATTERN);
    }
    public static boolean containsSpecialCharacter(String text){ return text.matches(SPECIAL_CHARACTERS_PATTERN); }
    public static boolean isValid24HoursString(String timeString, String splittingCharacter){ return timeString.matches(get24TimeFormatPattern(splittingCharacter)); }
    public boolean isValidPasswordPattern(String password, String passwordPattern){ return password.matches(passwordPattern); }
}
