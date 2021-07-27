package com.example.wolfbooksapp.vendor;

import java.text.DecimalFormat;

public class NumberFormatter {
    private static String[] SUFFIX = new String[]{
            "", "K", "M", "B", "T"
    };
    private static final int MAX_LENGTH = 5;

    public static String format(float number) {
        DecimalFormat mFormat = new DecimalFormat("###E00");
        String r = mFormat.format(number);
        int numericValue1 = Character.getNumericValue(r.charAt(r.length() - 1));
        int numericValue2 = Character.getNumericValue(r.charAt(r.length() - 2));
        int combined = Integer.valueOf(numericValue2 + "" + numericValue1);

        r = r.replaceAll("E[0-9][0-9]", SUFFIX[combined / 3]);

        while (r.length() > MAX_LENGTH || r.matches("[0-9]+\\.[a-z]")) {
            r = r.substring(0, r.length() - 2) + r.substring(r.length() - 1);
        }

        return r;
    }
}
