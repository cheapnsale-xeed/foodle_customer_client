package com.xeed.cheapnsale.util;

import java.text.DecimalFormat;

public class cheapnsaleUtils {

    public static String format(int number) {
        final DecimalFormat formatter = new DecimalFormat("#,###,###");

        return formatter.format(number);
    }

    public static String format(double number) {
        final DecimalFormat formatter = new DecimalFormat("#,###,###");

        return formatter.format(number);
    }
}
