package com.example.umc_insider.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationRegex {
    public ValidationRegex() {
    }

    public static boolean isRegexEmail(String target) {
        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex, 2);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
}

