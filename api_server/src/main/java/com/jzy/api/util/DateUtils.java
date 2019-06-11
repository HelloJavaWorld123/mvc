package com.jzy.api.util;

import com.google.common.base.Strings;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static final long HOURES_PER_DAY = 24;

    public static final long SECONDS_PER_MINUTE = 60;

    public static final long SECONDS_PER_HOURE = SECONDS_PER_MINUTE * SECONDS_PER_MINUTE;

    public static final long SECONDS_PER_DAY = HOURES_PER_DAY * SECONDS_PER_HOURE;


    /**
     * yyyy-MM-dd
     */
    public static final String DF_YMD = "yyyy-MM-dd";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String DF_Y_M_D_H_M_S = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyMMddHHmmssSSS
     */
    public static final String DF_YMDHMS = "yyMMddHHmmssSSS";
    /**
     * yyyyMMddHHmmss
     */
    public static final String DF_YYMDHMS = "yyyyMMddHHmmss";

    /**
     * 格式化时间 yyyy-MM-dd HH:mm:ss
     */
    public static String getFormatDate() {
        return getFormatDate(null, null, null);
    }

    /**
     * 格式化时间 yyyy-MM-dd HH:mm:ss
     */
    public static String getFormatDate(Date date) {
        return getFormatDate(date, null);
    }

    /**
     * 格式化时间 yyyy-MM-dd HH:mm:ss
     */
    public static String getFormatDate(String format) {
        return getFormatDate(null, format);
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static String getFormatDate(Date date, String format) {
        return getFormatDate(date, format, null);
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static String getFormatDate(String format, Locale locale) {
        return getFormatDate(null, format, locale);
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static String getFormatDate(Date date, String format, Locale locale) {
        if (date == null)
            date = new Date();
        if (locale == null)
            locale = Locale.CHINA;
        if (Strings.isNullOrEmpty(format))
            format = DF_Y_M_D_H_M_S;
        SimpleDateFormat formatter = new SimpleDateFormat(format, locale);
        return formatter.format(date);
    }

    /**
     * 字符转换为日期类型
     *
     * @param source
     * @param format
     * @return
     */
    public static Date formatStrToDate(String source, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(source);
        } catch (ParseException e) {
            return new Date();
        }
    }

    /**
     * 格式化当前时间星期
     */
    public static String getFormatWeek() {
        return getFormatWeek(new Date());
    }

    /**
     * 格式化指定时间星期
     */
    public static String getFormatWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int curr = cal.get(Calendar.DAY_OF_WEEK);
        String week = "星期六";
        switch (curr) {
            case 1:
                week = "星期日";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            default:
                week = "星期六";
                break;
        }
        return week;
    }

    /**
     * 计算当前日期*天后的日期
     *
     * @param amount
     * @return
     */
    public static Date calculateDate(int amount) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, amount);
        return cal.getTime();
    }

    /**
     * 计算指定日期*天后的日期
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date calculateDate(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, amount);
        return cal.getTime();
    }

    /**
     * 时间戳转换成时间
     */
    public static Date getDateByTimestamp(Long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        return cal.getTime();
    }

    /**
     * 计算相隔天数
     */
    public static long OperationDays(Date date1, Date date2) {
        return (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000);
    }

    /**
     * 计算相隔小时数
     */
    public static long OperationHours(Date date1, Date date2) {
        return (date1.getTime() - date2.getTime()) / (60 * 60 * 1000);
    }

    /**
     * 计算相隔分钟数
     */
    public static long OperationMinutes(Date date1, Date date2) {
        return (date1.getTime() - date2.getTime()) / (60 * 1000);
    }

    /**
     * 计算相隔秒数
     */
    public static long OperationSeconds(Date date1, Date date2) {
        return (date1.getTime() - date2.getTime()) / (1000);
    }

    /**
     * yyyy年MM月dd日
     */
    public static String getDateTimeStrC(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date);
    }

    /**
     * yyyy年MM月dd日 HH时mm分ss秒
     */
    public static String getDateTimeStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        return format.format(date);
    }

    /**
     * yyyy年MM月dd日 HH时
     */
    public static String getDateTimeStrD(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时");
        return format.format(date);
    }

    /**
     * yyyy年MM月dd日 HH:mm
     */
    public static String getDateTimeStrm(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        return format.format(date);
    }

    /**
     * 获得指定日期的年份
     */
    public static String getYear(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return format.format(date);
    }

    /**
     * 获得指定日期月份
     */
    public static String getMonth(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM");
        return format.format(date);
    }

    /**
     * 获得指定日期的日
     */
    public static String getDay(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd");
        return format.format(date);
    }

    /**
     * 获取相隔月数
     */
    @SuppressWarnings("deprecation")
    public static long getDistinceMonth(String beforedate, String afterdate) throws ParseException {
        SimpleDateFormat d = new SimpleDateFormat(DF_YMD);
        long monthCount = 0;
        try {
            Date d1 = d.parse(beforedate);
            Date d2 = d.parse(afterdate);

            monthCount = (d2.getYear() - d1.getYear()) * 12 + d2.getMonth() - d1.getMonth();
            // dayCount = (d2.getTime()-d1.getTime())/(30*24*60*60*1000);

        } catch (ParseException e) {
            // throw e;
        }
        return monthCount;
    }

    /**
     * 获取相隔天数
     */
    public static long getDistinceDay(String beforeDateTime, String afterDateTime) throws ParseException {
        SimpleDateFormat d = new SimpleDateFormat(DF_Y_M_D_H_M_S);
        long dayCount = 0;
        try {
            Date d1 = d.parse(beforeDateTime);
            Date d2 = d.parse(afterDateTime);

            dayCount = (d2.getTime() - d1.getTime()) / (24 * 60 * 60 * 1000);

        } catch (ParseException e) {
            throw e;
        }
        return dayCount;
    }

    /**
     * 获取相隔小时数
     */
    public static long getDistinceTime(String beforeDateTime, String afterDateTime) throws ParseException {
        SimpleDateFormat d = new SimpleDateFormat(DF_Y_M_D_H_M_S);
        long timeCount = 0;
        try {
            Date d1 = d.parse(beforeDateTime);
            Date d2 = d.parse(afterDateTime);
            timeCount = (d2.getTime() - d1.getTime()) / (60 * 60 * 1000);
        } catch (ParseException e) {
            throw e;
        }
        return timeCount;
    }

    /**
     * 获取相隔分钟数
     */
    public static long getDistinceMinute(String beforeDateTime, String afterDateTime) throws ParseException {
        SimpleDateFormat d = new SimpleDateFormat(DF_Y_M_D_H_M_S);
        long timeCount = 0;
        try {
            Date d1 = d.parse(beforeDateTime);
            Date d2 = d.parse(afterDateTime);
            timeCount = (d2.getTime() - d1.getTime()) / (60 * 1000);
        } catch (ParseException e) {
            throw e;
        }
        return timeCount;
    }

    /**
     * 获取相隔毫秒数
     */
    public static long getDistinceTimeMillis(String beforeDateTime, String afterDateTime) throws ParseException {
        SimpleDateFormat d = new SimpleDateFormat(DF_Y_M_D_H_M_S);
        long timeCount = 0;
        try {
            Date d1 = d.parse(beforeDateTime);
            Date d2 = d.parse(afterDateTime);
            timeCount = (d2.getTime() - d1.getTime());

        } catch (ParseException e) {
            throw e;
        }
        return timeCount;
    }

    /**
     * 获取指定时间x小时后的日期
     */
    public static Date addDate(String day, int x) {
        SimpleDateFormat format = new SimpleDateFormat(DF_Y_M_D_H_M_S);// 24小时制
        Date date = null;
        try {
            date = format.parse(day);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, x);// 24小时制
        date = cal.getTime();
        cal = null;
        return date;
    }

    /**
     * 获得学期第几周
     *
     * @param startDate
     * @return
     */
    public static Integer getWeekNum(Date startDate) {
        Integer weekNum = 0;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            int curr = cal.get(Calendar.DAY_OF_WEEK);
            long days = DateUtils.OperationDays(new Date(), cal.getTime()) + curr - 1;
            switch (curr) {
                case 1:
                    // 星期日
                    days += 6;
                    break;
                case 2:
                    // 星期一
                    break;
                case 3:
                    // 星期二
                    days += 1;
                    break;
                case 4:
                    // 星期三
                    days += 2;
                    break;
                case 5:
                    // 星期四
                    days += 3;
                    break;
                case 6:
                    // 星期五
                    days += 4;
                    break;
                default:
                    // 星期六
                    days += 5;
                    break;
            }
            if (days > 0) {
                weekNum = 0;// weekNum + MyString.getInteger(days / 7);
                if (days % 7 > 0) {
                    weekNum = weekNum + 1;
                }
            }
            // if (weekNum > 1) {
            // weekNum -= 1;
            // }
        } catch (Exception ex) {
            ex.printStackTrace();
            return weekNum;
        }
        return weekNum;
    }

    /**
     * 求某年下的第几个星期的日期 返回java.uilt.Date 类型日期 时间time为当前机器时间
     *
     * @param year 要获得的年
     * @param week 第几个星期
     * @param flag 是否是第一天还是最后一天,当为true时返回第一天,false则返回最后一天
     * @return java.uilt.Date 类型日期
     * @例如 getDayByWeek(2002, 2, true) 返回Tue Jan 08 14:11:57 CST 2002
     */
    public static Date getDayByWeek(int year, int week, boolean flag) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.get(Calendar.WEEK_OF_YEAR);
        cal.get(Calendar.WEEK_OF_MONTH);
        if (!flag)
            cal.setTimeInMillis(cal.getTimeInMillis() + 6 * 24 * 60 * 60 * 1000);
        return cal.getTime();
    }

    /**
     * 获取当前时间字符串('yyMMddhhmmSSS')
     */
    public static String getDateStr() {
        DateFormat date_str_df = new SimpleDateFormat(DF_YMDHMS);
        return date_str_df.format(new Date());
    }

    /**
     * 获取时间字符串：yyyyMMddHHmmss
     *
     * @param date
     * @return
     */
    public static String date2TimeStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DF_YYMDHMS);
        return format.format(date);
    }

    /**
     * 时间串转Date
     *
     * @param timeStr yyyyMMddHHmmss
     * @return
     * @throws ParseException
     */
    public static Date timeStr2Date(String timeStr) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.parse(timeStr);
    }

    /**
     * 比较当前日期与指定d在s后时间
     *
     * @param d
     * @param s
     * @return
     */
    public static boolean isOverdue(Date d, long s) {
        long operationSeconds = OperationSeconds(new Date(), d);
        return operationSeconds > s ? true : false;
    }

    /**
     * 获取本周任意一天日期
     *
     * @param day 1~7
     * @return 2017-07-14
     */
    public static String getThisWeek(int day, String format) {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        c.add(Calendar.DATE, -day_of_week + day);
        return new SimpleDateFormat(format).format(c.getTime());
    }

    /**
     * 获取指定日期时间戳（为null时默认为当前日期）
     */
    public static long getTimestamp(Date date) {
        Calendar c = Calendar.getInstance();
        if (date == null)
            return System.currentTimeMillis();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    /**
     * 获取时间戳
     */
    public static String getIntTimeStamp(Date date) {
        return Long.toString(getTimestamp(date) / 1000);
    }

    public static Date parse(String date, String format) {
        Date parse = null;
        if (null == date || "".equals(date)) {
            return new Date();
        }
        if (null == format || "".equals(format)) {
            format = DF_Y_M_D_H_M_S;
        }

        DateFormat sdf = new SimpleDateFormat(format);

        try {
            parse = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }

    /**
     * 比较年月日(>=当天日期)
     */
    public static Boolean isValid(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DF_YMD);
        return sdf.format(date).compareTo(sdf.format(new Date())) >= 0;
    }

    /**
     * 指定日期时间加减分钟
     */
    public static String getDateAddMinute(String dateTime, int minute, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = new Date(sdf.parse(dateTime).getTime() + minute * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(date);
    }

    /**
     * 指定日期获取星期
     */
    public static Integer getDateWeekNum(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取时间戳
     */
    public static String getIntTimeStamp(String source, String format) {
        Date date = formatStrToDate(source, format);
        return Long.toString(getTimestamp(date) / 1000);
    }

    public static String timeStampToString(Long timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat(DF_Y_M_D_H_M_S);
        return format.format(timeStamp);
    }

    /**
     * 获取指定年月 第一天和最后一天
     *
     * @param year
     * @param month
     * @param day    1:第一天, -1 最后一天
     * @param format 日期格式
     * @return
     */
    public static String getDayOfMonth(int year, int month, int day, String format) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        if (day == 1) {
            cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
        } else {
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        }
        return new SimpleDateFormat(format).format(cal.getTime());
    }

    /**
     * 一天结束还剩秒t数
     *
     * @param currentDate
     * @return
     */
    public static int getRemainSecondsOneDay(Date currentDate) {
        LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault()).plusDays(1).withHour(0).withMinute(0)
                .withSecond(0).withNano(0);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault());
        long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);
        return (int) seconds;
    }

    public static void main(String[] args) throws ParseException {
        Date dateStart = new Date();
        System.out.println(dateStart);



        Date date = formatStrToDate("2019-01-21 19:25:06",DF_Y_M_D_H_M_S);
        System.out.println(date);
    }

}
