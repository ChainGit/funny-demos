package com.chain.project.test;

import com.chain.project.common.utils.ChainProjectUtils;
import org.junit.Test;

import java.util.Date;

public class DateTimeTest {

    @Test
    public void test1() {
        String time = "2017-08-01 11:01:22";
        Date date = ChainProjectUtils.parseDateFormString(time);
        System.out.println(date);
        System.out.println(date.getTime());//1501556482000
    }

    @Test
    public void test2() {
        long time = 1501556482000L;
        Date date = ChainProjectUtils.convertLongToDate(time);
        String dateString = ChainProjectUtils.toFormatDateString(date);
        System.out.println(dateString);
    }

    @Test
    public void test3() {
        long time = 1501556482000L;
        Date date1 = new Date(time);
        Date date2 = new Date();
        int days = ChainProjectUtils.daysOfTwo(date1, date2);
        System.out.println(days);
    }
}
