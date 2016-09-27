package com.utraveler.validator.util;

import java.util.regex.Pattern;

public class ValidationUtil {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    public static boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        return email != null && pattern.matcher(email).matches();
    }


    public static boolean isTextInRange(String text, int min, int max) {
        return (text != null && text.length() >= min && text.length() <= max) || (text == null && min == 0);
    }


    public static boolean isTextEqualsLength(String text, int length) {
        return text != null && text.length() == length;
    }


    public static boolean isTextNotMore(String text, int max) {
        return isTextInRange(text, 0, max);
    }


    public static boolean isNumberInRange(Number number, double min, double max) {
        return number != null && number.doubleValue() >= min && number.doubleValue() <= max;
    }
}
