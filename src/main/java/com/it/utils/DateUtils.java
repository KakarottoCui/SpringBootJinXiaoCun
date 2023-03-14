package com.it.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**
     * 在给定的日期加上或减去指定月份后的日期
     *

     * @return
     */
    public static Date stepMonth(int i) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.add(Calendar.MONTH, -i);
        return c.getTime();
    }

    public static Date getMonth(int i) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -i);
        c.set(Calendar.DAY_OF_MONTH,c.getActualMaximum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY,23);
        c.set(Calendar.MINUTE,59);
        c.set(Calendar.SECOND,59);
        return c.getTime();
    }

    public static void main(String[] args) {
        Date month = DateUtils.getMonth(0);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(month));
        Date month1 = DateUtils.stepMonth(0);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(month1));
    }
}
