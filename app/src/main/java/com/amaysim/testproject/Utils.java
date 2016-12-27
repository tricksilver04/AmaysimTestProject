package com.amaysim.testproject;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by korn on 12/28/2016.
 */

public class Utils {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    public static String removeNull(String str) {
        if (str == null || str.equals("null"))
            return "--";
        return str;
    }

    public static String removeDashes(String str) {
        str = str.replace("-", " ");
        return str;
    }

    public static String mbToGB(String str) {
        String result = "--";
        try {
            double mbVal = Double.parseDouble(str);
            double gbVal = mbVal / 1000d;
            result = String.valueOf(gbVal) + " GB";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String centsToDollar(String cents) {
        String s = "--";
        try {
            NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);
            s = n.format(Long.parseLong(cents) / 100.0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
}
