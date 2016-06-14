package com.ouyben.androidutils.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 获取系统时间值
 * Created by ouyangbin on 2015/9/23.
 */
public class TimeUtils {
    /**
     * yyyy年MM月dd日 HH时mm分ss秒 星期几
     *
     * @return
     */
    public static String getTime1() {
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 EEEE");
        Log.e("time", "time1=" + format.format(date));
        return format.format(date);
    }

    /**
     * 年-月-日 时:分:秒
     *
     * @return
     */
    public static String getTime2() {
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Log.e("time", "time2=" + format.format(date));
        return format.format(date);
    }

    /**
     * 年/月/日
     *
     * @return
     */
    public static String getTime3() {
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Log.e("time", "time3=" + format.format(date));
        return format.format(date);
    }

    /**
     * 时:分:秒
     *
     * @return
     */
    public static String getTime4() {
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Log.e("time", "time4=" + format.format(date));
        return format.format(date);
    }

    /**
     * 星期几
     *
     * @return
     */
    public static String getTime5() {
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("EEEE");
        Log.e("time", "time5=" + format.format(date));
        return format.format(date);
    }

    /**
     * 周几
     *
     * @return
     */
    public static String getTime6() {
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("E");
        Log.e("time", "time6=" + format.format(date));
        return format.format(date);
    }
}
