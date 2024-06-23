package com.summary.common.core.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.StrUtil;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import static cn.hutool.core.date.DatePattern.PURE_DATE_PATTERN;


/**
 * @author jie.luo
 * @since 2024/5/29
 */
public class DateTimeUtils {

    /**
     * 默认时区
     */
    private static final String ZONE_ID_SHANGHAI = "Asia/Shanghai";
    /**
     * 每小时分钟数
     */
    public static final int MINUTES = 60;
    /**
     * 每日小时数
     */
    public static final int HOURS = 24;

    /**
     * 获得当天。
     *
     * @return 当天的时间，默认时区为"Asia/Shanghai"
     */
    public static LocalDate getNowDay() {
        return LocalDate.now(ZoneId.of(ZONE_ID_SHANGHAI));
    }

    /**
     * 获得当天。
     *
     * @return yyyyMMdd
     */
    public static String getNowDayString() {
        return convertLocalDateToString(getNowDay(), PURE_DATE_PATTERN);
    }

    /**
     * 根据传入格式获得当天。
     */
    public static String getNowDayString(String format) {
        return convertLocalDateToString(getNowDay(), format);
    }

    /**
     * 获得当前时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getNowDayTimeString() {
        return convertLocalDateTimeToString(getNow(), DatePattern.NORM_DATETIME_PATTERN);
    }

    /**
     * 获得当前系统的时间。
     *
     * @return 当前系统的时间，默认时区为"Asia/Shanghai"
     */
    public static LocalDateTime getNow() {
        return LocalDateTime.now(ZoneId.of(ZONE_ID_SHANGHAI));
    }

    /**
     * 将“LocalDate”类型的时间转换为“字符串”类型。
     *
     * @param localDate “LocalDate”类型的时间
     * @return “字符串”类型的时间（yyyy-MM-dd）
     */
    public static String convertLocalDateToString(LocalDate localDate) {
        localDate.atStartOfDay(ZoneId.of(ZONE_ID_SHANGHAI));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN);
        return localDate.format(dateTimeFormatter);
    }

    /**
     * 将“LocalDate”类型的时间，根据传入的格式转换为“字符串”类型。
     *
     * @param localDate “LocalDate”类型的时间
     * @param format    “字符串”类型的格式：支持yyyy（字符）MM（字符）dd
     * @return “字符串”类型的时间
     */
    public static String convertLocalDateToString(LocalDate localDate, String format) {
        localDate.atStartOfDay(ZoneId.of(ZONE_ID_SHANGHAI));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return localDate.format(dateTimeFormatter);
    }

    /**
     * 将“字符串”类型的时间转化为“LocalDate”类型。
     *
     * @param date   “字符串”类型的时间（yyyy-MM-dd）
     * @param format “字符串”类型的格式：支持yyyy（字符）MM（字符）dd
     * @return “LocalDate”类型的时间
     */
    public static LocalDate convertStringToLocalDate(String date, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);
        localDate.atStartOfDay(ZoneId.of(ZONE_ID_SHANGHAI));
        return localDate;
    }

    /**
     * 将“LocalDateTime”类型的时间转换为“字符串”类型。
     *
     * @param localDateTime 日期
     * @return 时间格式
     */
    public static String convertLocalDateTimeToString(LocalDateTime localDateTime, String format) {
        localDateTime.atZone(ZoneId.of(ZONE_ID_SHANGHAI));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(dateTimeFormatter);
    }

    /**
     * 将“字符串”类型的时间转化为“LocalDateTime”类型。
     *
     * @param dateTime “字符串”类型的时间（yyyyMMdd）
     * @return “LocalDateTime”类型的时间
     */
    public static LocalDateTime convertStringToLocalDateTime(String dateTime) {
        return convertStringToLocalDateTime(dateTime, PURE_DATE_PATTERN);
    }

    /**
     * 将“字符串”类型的时间转化为“LocalDateTime”类型。
     *
     * @param dateTime “字符串”类型的时间
     * @param format   “字符串”类型的时间 see {@link DatePattern}
     * @return “LocalDateTime”类型的时间
     */
    public static LocalDateTime convertStringToLocalDateTime(String dateTime, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dateTimeFormatter);
        localDateTime.atZone(ZoneId.of(ZONE_ID_SHANGHAI));
        return localDateTime;
    }

    /**
     * Date to LocalDateTime
     *
     * @param date Date
     * @return LocalDateTime
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.of(ZONE_ID_SHANGHAI);
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * 时间戳转换为 LocalDateTime
     *
     * @param timestamp 时间戳
     * @return LocalDateTime
     */
    public static LocalDateTime convertLocalDateTimeOfTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.of(ZONE_ID_SHANGHAI);
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * LocalDateTime -> 毫秒
     *
     * @return milliseconds
     */
    public static long convertLocalDateTimeToEpochMilli(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * LocalDate -> EpochDay
     *
     * @return epochDay
     */
    public static long convertLocalDateToEpochDay(LocalDate localDate) {
        return localDate.toEpochDay();
    }

    /**
     * EpochDay -> LocalDate
     *
     * @return LocalDate
     */
    public static LocalDate convertLocalDateOfEpochDay(long epochDay) {
        return LocalDate.ofEpochDay(epochDay);
    }

    /**
     * 获得当前系统时间（{@link Date}）。
     *
     * @return 当前系统时间
     */
    public static Date getNowDate() {
        LocalDateTime now = LocalDateTime.now();
        Instant instant = now.atZone(ZoneId.of(ZONE_ID_SHANGHAI)).toInstant();
        return Date.from(instant);
    }

    /**
     * 传入的“Date”转换为“LocalDateTime”类型。
     *
     * @param date 日期
     * @return LocalDateTime
     */
    public static LocalDateTime covertDateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.of(ZONE_ID_SHANGHAI)).toLocalDateTime();
    }

    /**
     * 转换LocalDateTime为Date
     *
     * @param localDateTime 时间
     * @return LocalDateTime
     */
    public static Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.toInstant(ZoneOffset.of("+8")));
    }

    /**
     * 转换dateTimeStr为Date
     *
     * @param dateTimeStr 时间
     * @return Date
     */
    public static Date convertStringToDate(String dateTimeStr) {
        if (StrUtil.isBlank(dateTimeStr)) {
            return null;
        }
        LocalDateTime localDateTime = convertStringToLocalDateTime(dateTimeStr);
        return Date.from(localDateTime.toInstant(ZoneOffset.of("+8")));
    }

    /**
     * 传入的“Date”转换为“LocalDate”类型。
     *
     * @param date 日期
     * @return LocalDateTime
     */
    public static LocalDate covertDateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.of(ZONE_ID_SHANGHAI)).toLocalDate();
    }

    /**
     * 获取当天的最后一刻
     *
     * @return LocalDateTime
     */
    public static LocalDateTime todayEndTime() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
    }

    /**
     * 获取当天最开始的时间
     *
     * @return LocalDateTime
     */
    public static LocalDateTime todayStartTime() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
    }

    /**
     * 获取某个时间的最后一刻
     *
     * @return LocalDateTime
     */
    public static LocalDateTime someTimeEnd(LocalDate localDate) {
        return LocalDateTime.of(localDate, LocalTime.MAX);
    }

    /**
     * 获取某个时间最开始的时间
     *
     * @return LocalDateTime
     */
    public static LocalDateTime someTimeStart(LocalDate localDate) {
        return LocalDateTime.of(localDate, LocalTime.MIN);
    }

    /**
     * 根据生日获取年龄
     *
     * @param birthday 生日
     * @return 年龄
     */
    public static Integer getAge(LocalDate birthday) {
        Calendar now = Calendar.getInstance();
        if (now.before(birthday)) {
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = now.get(Calendar.YEAR);
        int monthNow = now.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = now.get(Calendar.DAY_OF_MONTH);

        int yearBirth = birthday.getYear();
        int monthBirth = birthday.getMonthValue();
        int dayOfMonthBirth = birthday.getDayOfMonth();

        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                age--;
            }
        }
        return Math.max(age, 0);
    }

    /**
     * 获取时间属于春夏秋冬哪个季节
     *
     * @param localDate 当前时间
     * @return 1-春，2-夏，3-秋，4-冬
     */
    public static int getDateSeason(LocalDate localDate) {
        int value = localDate.getMonth().getValue();
        if (value >= 3 && value < 6) {
            return 1;
        } else if (value >= 6 && value < 9) {
            return 2;
        } else if (value >= 9 && value < 12) {
            return 3;
        }
        return 4;
    }

    /**
     * 获取某年的开始时间与结束时间
     *
     * @param today   时间
     * @param isFirst true-开始时间，false-结束时间
     * @return 某年的开始时间与结束时间
     */
    public static String getStartOrEndDayOfYear(LocalDate today, Boolean isFirst) {
        LocalDate resDate = LocalDate.now();
        if (today == null) {
            today = resDate;
        }
        if (isFirst) {
            resDate = LocalDate.of(today.getYear(), Month.JANUARY, 1);
        } else {
            resDate = LocalDate.of(today.getYear(), Month.DECEMBER, Month.DECEMBER.length(today.isLeapYear()));
        }
        return resDate.toString();

    }

    /**
     * 获取某季度的开始时间与结束时间
     *
     * @param today   某季度
     * @param isFirst true-开始时间，false-结束时间
     * @return 获取某季度的开始时间与结束时间l
     */
    public static String getStartOrEndDayOfQuarter(LocalDate today, Boolean isFirst) {
        LocalDate resDate = LocalDate.now();
        if (today == null) {
            today = resDate;
        }
        Month month = today.getMonth();
        Month firstMonthOfQuarter = month.firstMonthOfQuarter();
        Month endMonthOfQuarter = Month.of(firstMonthOfQuarter.getValue() + 2);
        if (isFirst) {
            resDate = LocalDate.of(today.getYear(), firstMonthOfQuarter, 1);
        } else {
            resDate = LocalDate.of(today.getYear(), endMonthOfQuarter, endMonthOfQuarter.length(today.isLeapYear()));
        }
        return resDate.toString();
    }

    /**
     * 获取某月的第一天或最后一天
     *
     * @param today   某月
     * @param isFirst true-开始时间，false-结束时间
     * @return 获取某月的第一天或最后一天
     */
    public static String getStartOrEndDayOfMonth(LocalDate today, Boolean isFirst) {
        LocalDate resDate = LocalDate.now();
        if (today == null) {
            today = resDate;
        }
        Month month = today.getMonth();
        int length = month.length(today.isLeapYear());
        if (isFirst) {
            resDate = LocalDate.of(today.getYear(), month, 1);
        } else {
            resDate = LocalDate.of(today.getYear(), month, length);
        }
        return resDate.toString();
    }
}
