package com.lxgzhw.lxgSSMUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class LxgDate {
    public static Date now = new Date();
    public static SimpleDateFormat sdfCommon = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat sdfChinese = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

    public static void main(String[] args) {
        System.out.println(transformStringToDate("2022年11月11日 22:22:22"));
    }

    /**
     * 将Date日期类型转换为中文的日期字符串
     *
     * @param date Date日期对象
     * @return 格式化日期字符串
     */
    public static String transformDateToString(Date date) {
        return sdfChinese.format(date);
    }

    /**
     * 将字符串日期转换为Date日期类型
     *
     * @param str 日期字符串
     * @return Date日期类型
     */
    public static Date transformStringToDate(String str) {
        if (str.contains("年")) {
            try {
                return sdfChinese.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            try {
                return sdfCommon.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        System.out.println("暂时不支持此类型的日期转换");
        return null;
    }

    /**
     * 判断是不是闰年
     *
     * @param year 年份
     * @return 判断结果
     */
    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * 输入出生的年月日,计算活了多少天
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return 活了多少天的数
     */
    public static long getLiveDays(int year, int month, int day) {
        Date birth = getDateByYearMonthDay(year, month, day);
        return betweenDays(birth, now);
    }

    /**
     * 输入出生年月日,获取年龄
     *
     * @param year  年份
     * @param month 月份
     * @param day   日
     * @return 年龄
     */
    public static long getAge(int year, int month, int day) {
        Date birth = getDateByYearMonthDay(year, month, day);
        return betweenYears(birth, now);
    }

    /**
     * 计算距离指定时间还剩下多少天
     *
     * @param to 指定日期
     * @return 相差天数
     */
    public static long leftDays(Date to) {
        return betweenDays(now, to);
    }

    /**
     * 计算距离指定时间还剩下多少小时
     *
     * @param to 指定时间
     * @return 相差小时数
     */
    public static long leftHour(Date to) {
        return betweenHour(now, to);
    }

    /**
     * 计算两个日期之间相差多少小时
     *
     * @param from 开始时间
     * @param to   结束时间
     * @return 相差小时数
     */
    public static long betweenHour(Date from, Date to) {
        long temp = betweenMinute(from, to);
        return temp % 60 == 0 ? temp / 60 : temp / 60 + 1;
    }

    /**
     * 计算到指定时间还剩下多少分钟
     *
     * @param to 指定日期
     * @return 剩下分钟数
     */
    public static long leftMinute(Date to) {
        return betweenMinute(now, to);
    }

    /**
     * 计算两个日期之间相差了多少分钟
     *
     * @param from 开始时间
     * @param to   结束时间
     * @return 相差分钟数
     */
    public static long betweenMinute(Date from, Date to) {
        long temp = betweenSecond(from, to);
        return temp % 60 == 0 ? temp / 60 : temp / 60 + 1;
    }

    /**
     * 计算当前时间到指定时间还剩下多少秒
     *
     * @param to 指定日期
     * @return 剩下秒数
     */
    public static long leftSecond(Date to) {
        return betweenSecond(now, to);
    }

    /**
     * 计算两个日期之间相差了多少秒
     *
     * @param from 开始日期
     * @param to   结束日期
     * @return 相差秒数
     */
    public static long betweenSecond(Date from, Date to) {
        long temp = betweenMillisecond(from, to);
        return temp % 1000 == 0 ? temp / 1000 : temp / 1000 + 1;
    }

    /**
     * 计算当前时间,距离指定时间相差了多少毫秒
     *
     * @param to 指定的时间
     * @return 相差毫秒
     */
    public static long leftMillisecond(Date to) {
        return betweenMillisecond(now, to);
    }

    /**
     * 计算两个日期之间相差了多少毫秒
     *
     * @param from 开始时间
     * @param to   结束时间
     * @return 相差毫秒
     */
    public static long betweenMillisecond(Date from, Date to) {
        return to.getTime() - from.getTime();
    }

    /**
     * 计算两个日期之间相差了多少年
     *
     * @param from 开始日期
     * @param to   结束日期
     * @return 相差年费
     */
    public static long betweenYears(Date from, Date to) {
        return transformDateToLocalDate(to).getYear() - transformDateToLocalDate(from).getYear();
    }

    /**
     * 计算两个Date日期之间相差的天数
     *
     * @param from 开始日期
     * @param to   结束日期
     * @return 相差天数
     */
    public static long betweenDays(Date from, Date to) {
        return transformDateToLocalDate(to).toEpochDay() - transformDateToLocalDate(from).toEpochDay();
    }

    /**
     * 根据年月日获得Date类型的日期对象
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return Date类型日期对象
     */
    public static Date getDateByYearMonthDay(int year, int month, int day) {
        LocalDate localDate = LocalDate.of(year, month, day);
        return transformLocalDateToDate(localDate);
    }

    /**
     * 格式化打印今天是日期的方法
     */
    public static void printNow() {
        String format = sdfChinese.format(now);
        System.out.println(format);
    }

    /**
     * 将LocalDate类型转换为Date类型
     *
     * @param localDate LocalDate时间对象
     * @return Date时间对象
     */
    public static Date transformLocalDateToDate(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }

    /**
     * 将Date日期对象转换为LocalDate日期对象
     *
     * @param date Date日期对象
     * @return LocalDate日期对象
     */
    public static LocalDate transformDateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();

        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        return localDate;
    }
}
