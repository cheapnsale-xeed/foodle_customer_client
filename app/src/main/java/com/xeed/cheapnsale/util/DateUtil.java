package com.xeed.cheapnsale.util;

import org.joda.time.DateTimeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    private static SimpleDateFormat dateFormat =  new SimpleDateFormat("yyyy.MM.dd_HH:mm:ss", Locale.getDefault());
    private static SimpleDateFormat timeFormat =  new SimpleDateFormat("HH:mm", Locale.getDefault());

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

    public static String calcPickupTime(String currentTime, String prepTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(stringToDate(currentTime));
        calendar.add(Calendar.MINUTE, Integer.parseInt(prepTime));

        return dateFormat.format(calendar.getTime());
    }

    public static String getCurrentDateTime() {
        return dateFormat.format(new Date(DateTimeUtils.currentTimeMillis()));
    }

    public static String getCurrentTime() {
        return timeFormat.format(new Date(DateTimeUtils.currentTimeMillis()));
    }

    public static int calcTimeGap(String endTime, String startTime) {

        String convertStartTime, convertEndTime;
        long gapTime = 0;

        if(Integer.parseInt(startTime.substring(0,2)) < 6) {
            convertStartTime = "2017.01.03_" + startTime + ":00";
        } else {
            convertStartTime = "2017.01.02_" + startTime + ":00";
        }
        if(Integer.parseInt(endTime.substring(0,2)) < 6) {
            convertEndTime = "2017.01.03_" + endTime + ":00";
        } else {
            convertEndTime = "2017.01.02_" + endTime + ":00";
        }

        gapTime = stringToDate(convertEndTime).getTime() - stringToDate(convertStartTime).getTime();

        return (int) (gapTime/1000/60) ;
    }
}
