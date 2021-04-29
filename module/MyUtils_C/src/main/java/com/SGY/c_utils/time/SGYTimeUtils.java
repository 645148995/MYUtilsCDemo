package com.SGY.c_utils.time;

import android.text.TextUtils;

import com.SGY.c_utils.content.SGYLogUtils;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 时间工具类
 * 官网：https://www.joda.org/joda-time/
 * 参考示例：joda time https://blog.csdn.net/wjsshhx/article/details/62422844
 */
public class SGYTimeUtils {

    public static final String FORMATE_DATE = "yyyy-MM-dd";
    public static final String FORMATE_SECONDS = "HH:mm:ss";
    public static final String FORMATE_FULL = FORMATE_DATE.concat(" ").concat(FORMATE_SECONDS);

    /**
     * 将字符串日期，格式化指定格式
     *
     * @param strTime 字符串格式的时间
     * @param format  yyyy-MM-dd HH:mm:ss 自定义时间格式
     */
    public static String format(String strTime, String format) {
        if (TextUtils.isEmpty(strTime))
            return "";
        strTime = strTime.replaceAll("/", "-");
        try {
            return format(getMillis(strTime), format);
        } catch (Exception e) {
            SGYLogUtils.e(e);
        }
        return "";
    }

    /**
     * 将时间戳，格式化指定格式
     *
     * @param longTime 13位时间戳
     * @param format   yyyy-MM-dd HH:mm:ss 自定义时间格式
     */
    public static String format(long longTime, String format) {
        try {
            DateTimeFormatter dtf = DateTimeFormat.forPattern(format);
            DateTime dateTime = new DateTime(longTime);
            return dateTime.toString(dtf);
        } catch (Exception e) {
            SGYLogUtils.e(e);
        }
        return "";
    }

    /**
     * @param longTime 时间戳
     * @param format   格式 yyyy-MM-dd HH:mm:ss
     * @param timeZone 时区 GMT+08
     */
    public static String format(long longTime, String format, String timeZone) {
        SimpleDateFormat dff = new SimpleDateFormat(format);
        dff.setTimeZone(TimeZone.getTimeZone(timeZone));
        return dff.format(new Date(longTime));
    }

    /**
     * 将字符串日期，转换为时间戳
     *
     * @param strTime 字符串格式的时间
     */
    public static long getMillis(String strTime) {
        if (TextUtils.isEmpty(strTime))
            return 0l;
        strTime = strTime.replaceAll("/", "-");
        try {
            DateTimeFormatter fmt = DateTimeFormat.forPattern(FORMATE_FULL);
            return fmt.parseMillis(strTime);
        } catch (Exception e) {
            SGYLogUtils.e(e);
        }
        return 0l;
    }

    /**
     * 计算区间年、月、天、小时、分钟、秒
     */
    public static Period period(String beginTime, String endTime) {
        if (TextUtils.isEmpty(beginTime) || TextUtils.isEmpty(endTime))
            return null;
        try {
            return period(getMillis(beginTime), getMillis(endTime));
        } catch (Exception e) {
            SGYLogUtils.e(e);
        }
        return null;
    }

    /**
     * 计算区间年、月、天、小时、分钟、秒
     */
    public static Period period(long beginTime, long endTime) {
        try {
            DateTime begin = new DateTime(beginTime);
            DateTime end = new DateTime(endTime);
            Period period = new Period(begin, end);
            return period;
        } catch (Exception e) {
            SGYLogUtils.e(e);
        }
        return null;
    }

    /**
     * 两个时间之间所差的天、小时、分钟、秒
     */
    public static Duration duration(String beginTime, String endTime) {
        if (TextUtils.isEmpty(beginTime) || TextUtils.isEmpty(endTime))
            return null;
        try {
            return duration(getMillis(beginTime), getMillis(endTime));
        } catch (Exception e) {
            SGYLogUtils.e(e);
        }
        return null;
    }

    /**
     * 两个时间之间所差的天、小时、分钟、秒
     */
    public static Duration duration(long beginTime, long endTime) {
        try {
            DateTime begin = new DateTime(beginTime);
            DateTime end = new DateTime(endTime);
            Duration duration = new Duration(begin, end);
            return duration;
        } catch (Exception e) {
            SGYLogUtils.e(e);
        }
        return null;
    }

    /**
     * 判断时间跨度是否包含当前时间，某个时间
     */
    public static Interval interval(String beginTime, String endTime) {
        if (TextUtils.isEmpty(beginTime) || TextUtils.isEmpty(endTime))
            return null;
        try {
            return interval(getMillis(beginTime), getMillis(endTime));
        } catch (Exception e) {
            SGYLogUtils.e(e);
        }
        return null;
    }

    /**
     * 判断时间跨度是否包含当前时间，某个时间
     */
    public static Interval interval(long beginTime, long endTime) {
        try {
            DateTime begin = new DateTime(beginTime);
            DateTime end = new DateTime(endTime);
            Interval interval = new Interval(begin, end);
            return interval;
        } catch (Exception e) {
            SGYLogUtils.e(e);
        }
        return null;
    }

    /***
     * @param milliSecond 毫秒
     * @return 00:00:00 （分隔符有个性的，自己replace 或 split(";")）
     */
    public static String duration(long milliSecond, boolean showHour) {
        int totalSeconds = (int) (milliSecond / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        if (showHour)
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        else
            return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes,
                    seconds) : String.format("%02d:%02d", minutes, seconds);
    }

}
