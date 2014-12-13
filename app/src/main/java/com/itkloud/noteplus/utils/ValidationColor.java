package com.itkloud.noteplus.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by andressh on 20/11/14.
 */
public class ValidationColor {

    private static Pattern pattern;
    private static Matcher matcher;

    private static final String HEX_PATTERN = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";

    static {
        pattern = Pattern.compile(HEX_PATTERN);
    }

    public static boolean validate(String hexColorCode) {
        if(hexColorCode == null) return false;
        matcher = pattern.matcher(hexColorCode);
        return matcher.matches();

    }

}
