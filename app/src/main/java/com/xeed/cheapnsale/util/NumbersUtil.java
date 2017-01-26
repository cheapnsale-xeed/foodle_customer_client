package com.xeed.cheapnsale.util;

import java.text.DecimalFormat;

public class NumbersUtil {

    final static DecimalFormat formatter = new DecimalFormat("#,###,###");

    public static String format(int number) {
        return formatter.format(number);
    }

    public static String format(double number) {
        return formatter.format(number);
    }
}
