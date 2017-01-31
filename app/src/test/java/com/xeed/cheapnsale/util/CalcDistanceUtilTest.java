package com.xeed.cheapnsale.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class CalcDistanceUtilTest {
    @Test
    public void getDistanceFromAtoB() throws Exception {
        assertEquals((int) CalcDistanceUtil.calDistance(37.517646d, 127.101843d, 37.5164221d, 127.1014379d), 140);
    }
}