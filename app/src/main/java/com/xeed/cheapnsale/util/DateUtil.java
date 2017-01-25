package com.xeed.cheapnsale.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    private static SimpleDateFormat dateFormat =  new SimpleDateFormat("yyyy.MM.dd_HH:mm:ss", Locale.getDefault());

    public static Date stringToDate(String dateStr) {

        Date formattedDate = new Date();
        SimpleDateFormat transFormat = dateFormat;
        try {
            formattedDate = transFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public static String dateToString(Date date) {

        SimpleDateFormat transFormat = dateFormat;
        return transFormat.format(date);

    }

    public static String myOrderPickUpTime(String pickUpTime) {

        String formattedTime;

        String[] hms = pickUpTime.split("_")[1].split(":");
        String hour = hms[0];
        String min = hms[1];
        int iHour = Integer.parseInt(hour);

        if (iHour > 12) {
            iHour -= 12;
            formattedTime = "오후 " + String.valueOf(iHour) + "시 ";
        } else {
            formattedTime = "오전 " + String.valueOf(iHour) + "시 ";
        }
        formattedTime += min + "분";

        return formattedTime;
    }

}
