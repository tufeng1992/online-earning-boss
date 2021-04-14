package com.powerboot.common.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理
 */
public class DateUtils {
    private final static Logger logger = LoggerFactory.getLogger(DateUtils.class);
    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String DATEHMSFORMAT = "23:59:59";
    public static final String SIMPLE_DATEFORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String SIMPLE_DATEFORMAT2 = "yyyy/MM/dd HH:mm:ss";

    public static final String SIMPLE_DATEFORMAT_HM = "yyyy-MM-dd HH:mm";

    public static final String SIMPLE_DATEFORMATHH = "yyyy-MM-dd HH:00:00";
    public static final String SIMPLE_DATEFORMAYYYYMMDDHH = "yyyy-MM-dd HH:00";

    public static final String SIMPLE_DATEFORMAT_YMDHMSS = "yyyyMMddHHmmssSSS";

    public static final String SIMPLE_DATEFORMAT_MD = "MM.dd";

    public static final String SIMPLE_DATEFORMAT_MD2 = "MMdd";

    public static final String SIMPLE_DATEFORMAT_HHMM = "HH:mm";
    public static final String SIMPLE_DATEFORMAT_HHMM2 = "HH:00";
    public static final String SIMPLE_DATEFORMAT_YMD = "yyyy-MM-dd";
    public static final String SIMPLE_DATEFORMAT_YM = "yyyy-MM";
    public static final String SIMPLE_DATEFORMAT_YMD2 = "yyyyMMdd";
    public static final String SIMPLE_DATEFORMAT_SPECAIL = "MM月dd日HH:mm";

    public static final String SIMPLE_DATEFORMAT_YMD_CHINESE = "yyyy年MM月dd日";

    public static final String SIMPLE_DATEFORMAT_YMD3 = "yyyy/MM/dd";
    public static final String SIMPLE_DATEFORMAT_YMD4 = "MM/dd/yyyy";
    public static final String SIMPLE_DATEFORMAT_Y = "yyyy";

    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 计算距离现在多久，非精确
     *
     * @param date
     * @return
     */
    public static String getTimeBefore(Date date) {
        Date now = new Date();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String r = "";
        if (day > 0) {
            r += day + "天";
        } else if (hour > 0) {
            r += hour + "小时";
        } else if (min > 0) {
            r += min + "分";
        } else if (s > 0) {
            r += s + "秒";
        }
        r += "前";
        return r;
    }

    /**
     * 计算距离现在多久，精确
     *
     * @param date
     * @return
     */
    public static String getTimeBeforeAccurate(Date date) {
        Date now = new Date();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String r = "";
        if (day > 0) {
            r += day + "天";
        }
        if (hour > 0) {
            r += hour + "小时";
        }
        if (min > 0) {
            r += min + "分";
        }
        if (s > 0) {
            r += s + "秒";
        }
        r += "前";
        return r;
    }

    public static Date getTodayStart(){
        Date now  = new Date();
        String nowStr = DateUtils.format(now,DATE_PATTERN);
        nowStr = nowStr + " 00:00:00";
        return DateUtils.parseDate(nowStr,DATE_TIME_PATTERN);
    }

    public static Date parseDate(String date, String pattern) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date d = null;
        try {
            d = format.parse(date);
        } catch (ParseException e) {
           logger.error("日期转换出错",date,e);
        }

        return d;
    }

    public static Date localDateToUdate(LocalDate currDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = currDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);

    }

    public static Date addNumDays(Date currDate,int num) {
        Calendar c = Calendar.getInstance();
        c.setTime(currDate);
        c.add(Calendar.DAY_OF_MONTH, num);
        return c.getTime();
    }

    public static Date addMinutes(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

}
