package com.xeed.cheapnsale.util;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;


public class DateUtilTest {

    private String sampleDateStr = "2017.01.24_17:29:00";
    private Date sampleDateByLong = new Date(1485246540000L);
    private String samplePickUpTimeStr = "오후 5시 29분";


    @Test
    public void stringToDateTest() throws Exception {

        Date date = DateUtil.stringToDate(sampleDateStr);
        assertEquals(date, sampleDateByLong);
    }

    @Test
    public void dateToStringTest() throws Exception {

        String str = DateUtil.dateToString(sampleDateByLong);
        assertEquals(str, sampleDateStr);

    }

    @Test
    public void myOrderPickUpTimeTest() throws Exception {

        String pickupTime = DateUtil.myOrderPickUpTime(sampleDateStr);
        assertEquals(pickupTime, samplePickUpTimeStr);

    }
}


