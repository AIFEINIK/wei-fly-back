package com.wei.fly.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateUtils {

    public static final String DATE_FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_MINUTE = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT_SHORT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_COMPACT = "yyyyMMdd";
    public static final String DATE_FORMAT_FULL_MSEL = "yyyyMMddHHmmssSSSS";
    public static final String DATE_FORMAT_COMPACTFULL = "yyyyMMddHHmmss";
    public static final String DATE_YEAR_MONTH = "yyyyMM";
    //这个时间段预约到第二天
    public static final LocalTime orderMinTime = LocalTime.parse("21:59:59");
    public static final LocalTime orderMaxTime = LocalTime.parse("23:59:59.999");

    //这个时间段不允许预约
    public static final LocalTime forbidOrderMinTime = LocalTime.parse("21:00:00");
    public static final LocalTime forbidOrderMaxTime = LocalTime.parse("22:00:00");

    /**
     * 获取系统当前日期
     *
     * @return
     */
    public static Date getCurrentDate() {
        return new Date();
    }

    /**
     * 获取系统当前日期
     *
     * @return
     */
    public static LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 根据时间格式返回对应的String类型的时间
     *
     * @param format
     * @return
     */
    public static String getCurDateTime(String format) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        String dataTime = now.format(dateTimeFormatter);
        return dataTime;
    }

    public static String getEndTime(String dateStr) {
        final Date date = parseStringToDate(dateStr, DATE_FORMAT_SHORT);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        Date d = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
        return parseDateToString(d, DATE_FORMAT_FULL);
    }

    /**
     * 得到当前日期
     *
     * @return String 当前日期 yyyy-MM-dd HH:mm:ss格式
     */
    public static String getCurDateTimeFull() {
        return getCurDateTime(DATE_FORMAT_FULL);
    }


    /**
     * 得到当前日期
     *
     * @return String 当前日期 yyyyMMddHHmmss格式
     */
    public static String getCurDateTime1() {
        return getCurDateTime(DATE_FORMAT_FULL);
    }

    /**
     * 得到当前日期YYYYMM格式
     *
     * @return String 当前日期 yyyyMM格式
     */
    public static String getCurDateYYYYMM() {
        return getCurDateTime(DATE_YEAR_MONTH);
    }


    /**
     * 得到当前日期
     *
     * @return String 当前日期 yyyyMMdd格式
     */
    public static String getCurDateYYYYMMDD() {
        return getCurDateTime(DATE_FORMAT_COMPACT);
    }

    /**
     * 判断是否是今天
     *
     * @param strDate
     * @return
     */
    public static boolean isCurrentDay(String strDate) {
        boolean bRet = false;
        LocalDate strLocalDate = LocalDate.parse(strDate);
        if (LocalDate.now().getYear() == strLocalDate.getYear()) {
            MonthDay monthDay = MonthDay.from(strLocalDate);
            MonthDay today = MonthDay.from(LocalDate.now());
            return monthDay.equals(today);
        }
        return bRet;
    }

    /**
     * 获取几小时后的时间
     *
     * @param hour
     * @param format
     * @return
     */
    public static String getAfterDateTime(int hour, String format) {
        LocalTime localTime = LocalTime.now().plusHours(hour);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        String dataTime = localTime.format(dateTimeFormatter);
        return dataTime;
    }


    /**
     * 当前日期时间戳(yyyyMMddHHmmssSSSS)
     *
     * @return
     */
    public static String getTimeStamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT_FULL_MSEL);
        return now.format(dateTimeFormatter);
    }

    /**
     * 日期转字符串
     *
     * @return String
     */
    public static String parseDateToString(Date date, String format) {
        if (date != null) {
            Instant instant = date.toInstant();
            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
            return localDateTime.format(dateTimeFormatter);
        }
        return null;
    }

    /**
     * parseDateToString(Date thedate, String format)的重载方法
     *
     * @param date
     * @return
     */
    public static String parseDateToString(Date date) {
        // String format = "yyyy-MM-dd";
        return parseDateToString(date, DATE_FORMAT_FULL);
    }

    /**
     * 字符串转日期
     *
     * @return Date
     */
    public static Date parseStringToDate(String thedate, String format) {
        DateFormat sdf = new SimpleDateFormat(format);
        Date dd1 = null;
        try {
            dd1 = sdf.parse(thedate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dd1;
    }

    /**
     * 由String型日期转成format形式String
     *
     * @param format1 原先格式
     * @param format2 转化格式
     * @return String
     */
    public static String changeFormatDateString(String format1, String format2, String strDate) {
        if (strDate == null)
            return "";
        if (strDate.length() >= format1.length() && format1.length() >= format2.length()) {
            return parseDateToString(parseStringToDate(strDate, format1), format2);
        }
        return strDate;
    }

    /**
     * 得到当前日期的前N天时间 yyyymmdd
     *
     * @param format
     * @param day
     * @return
     */
    public static String beforeNDaysDate(String format, int day) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        if (day > 0) {
            return LocalDateTime.now().minusDays(day).format(dateTimeFormatter);
        }
        return null;
    }

    /**
     * 获得N个月后的日期
     * <p>
     * theDate 日期
     * <p>
     * int month 月数
     * <p>
     * format 格式
     */
    public static String afterNMonthDate(String theDate, int month, String format) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(theDate,dateTimeFormatter)
                .plusMonths(month)
                .format(dateTimeFormatter);

    }

    /**
     * 得到N天后的日期
     *
     * @param theDate 某日期
     *                格式 yyyy-MM-dd
     * @param nDayNum N天
     * @return String N天后的日期
     */
    public static String afterNDaysDate(String theDate, Integer nDayNum, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);

        return LocalDateTime.parse(theDate,dateTimeFormatter)
                .plusDays(nDayNum)
                .format(dateTimeFormatter);
    }

    /**
     * 得到N小时后的日期
     *
     * @param theDate  时间
     * @param nHourNum N小时数
     * @param format   时间格式
     * @return
     */
    public static String afterNHoursDate(String theDate, Integer nHourNum, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(theDate,dateTimeFormatter)
                .plusHours(nHourNum)
                .format(dateTimeFormatter);
    }

    /**
     * 得到N分钟后的日期
     *
     * @param theDate
     * @param nMinNum
     * @param format
     * @return
     */
    public static String afterNMinsDate(String theDate, Integer nMinNum, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(theDate,dateTimeFormatter)
                .plusMinutes(nMinNum)
                .format(dateTimeFormatter);
    }

    /**
     * 得到N秒后的日期
     * @param theDate
     * @param nSecNum
     * @param format
     * @return
     */
    public static String afterNSecondsDate(String theDate, Integer nSecNum, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(theDate,dateTimeFormatter)
                .plusSeconds(nSecNum)
                .format(dateTimeFormatter);
    }

    /**
     * 方法描述:
     * 计算两个日期相差天数
     *
     * @param end   结束日期
     * @param start 开始日期
     */
    public static int getSubDays(String end, String start) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        Long between = ChronoUnit.DAYS.between(startDate, endDate);
        return between.intValue();
    }

    public static Date handleUseTime(LocalTime useTime) {
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), useTime);
        LocalTime nowTime = LocalTime.now();
        if (nowTime.isAfter(orderMinTime) && nowTime.isBefore(orderMaxTime)) {
            //预约到第二天
            dateTime = LocalDateTime.of(LocalDate.now().plusDays(1), useTime);

        }
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 是否在禁止预约时间范围内
     * @return
     */
    public static boolean isForbidOrderTime() {
        LocalTime nowTime = LocalTime.now();
        return nowTime.isAfter(forbidOrderMinTime) && nowTime.isBefore(forbidOrderMaxTime);
    }

    public static void main(String[] args) {
        final Date date = handleUseTime(LocalTime.parse("22:00"));
        System.out.println(parseDateToString(date, DATE_FORMAT_FULL));
    }
}
