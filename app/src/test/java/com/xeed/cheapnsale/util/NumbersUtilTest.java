package com.xeed.cheapnsale.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class NumbersUtilTest {
    @Test
    public void formatterTest() throws Exception {
        assertEquals(NumbersUtil.format(123), "123");
        assertEquals(NumbersUtil.format(1234), "1,234");
        assertEquals(NumbersUtil.format(12345), "12,345");
        assertEquals(NumbersUtil.format(123456), "123,456");
        assertEquals(NumbersUtil.format(1234567), "1,234,567");
        assertEquals(NumbersUtil.format(12345678), "12,345,678");
        assertEquals(NumbersUtil.format(123456789), "123,456,789");
        assertEquals(NumbersUtil.format(1234567890), "1,234,567,890");
    }
}