package com.fire.translation.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by fire on 2018/1/12.
 * Date：2018/1/12
 * Author: fire
 * Description:
 */

public class DateUtils {
    /**
     * yyyy-MM-dd
     */
    public static final String dateFormat1 = "yyyy-MM-dd";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String dateFormat2 = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyy-MM-dd-HH-mm-ss
     */
    public static final String dateFormat3 = "yyyy-MM-dd-HH-mm-ss";
    /**
     * yyyy/MM/dd HH:mm:ss
     */
    public static final String dateFormat4 = "yyyy/MM/dd HH:mm:ss";
    /**
     * MM-dd,HH:mm
     */
    public static final String dateFormat5 = "MM-dd,HH:mm";
    /**
     * yyyyMMddHHmmssSSS
     */
    public static final String dateFormat6 = "yyyyMMddHHmmssSSS";
    /**
     * yyyyMMdd_HHmmss
     */
    public static final String dateFormat7 = "yyyyMMdd_HHmmss";
    /**
     * yyyy-MM-dd HH:mm
     */
    public static final String dateFormat8 = "yyyy-MM-dd HH:mm";
    /**
     * yyyy-MM-dd HH:mm
     */
    public static final String dateFormat9 = "MM-dd HH:mm";
    /**
     * HHmmssSSS
     */
    public static final String dateFormat10 = "ssSSS";
    /**
     * yyyyMMdd_HHmmssSSS
     */
    public static final String dateFormat11 = "yyyyMMdd_HHmmssSSS";
    /**
     * yyyy-MM-dd HH:mm
     */
    public static final String dateFormat12 = "yyyy.MM.dd HH:mm";

    public static final String dateFormat13 = "yyyyMMdd";


    public static String formatDateToString (Date date,String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static String formatDateToString (String date,String format) {
        return formatDateToString(Long.parseLong(date),format);
    }

    public static String formatDateToString (long date,String format) {
        return formatDateToString(new Date(date),format);
    }

    /**
     * 字符串转换成日期
     * @param str
     * @return date
     */
    public static Date parseToDate(String str,String format) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = dateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String parseToString(String str,String format) {
        Date date = parseToDate(str, format);
        return date == null? "":date.getTime() + "";
    }

    public static String formatDateInWeek(String date,String format,String format2) {
        String dateStr = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.CHINA);
            ParsePosition pos = new ParsePosition(0);
            SimpleDateFormat formatter2 = new SimpleDateFormat(format2, Locale.CHINA);
            dateStr = formatter2.format(formatter.parse(date, pos));
        } catch (Exception e) {
            dateStr = "";
        }
        return dateStr;
    }

    public static void  test(int cdd) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, cdd);
        date = calendar.getTime();
        System.out.println(sdf.format(date));
    }

    public static String getFormatWeek(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日 EEEE");
        String format = dateFormat.format(date);
        return format+"";
    }

    public static String getFormatDate1(Date date) {
        return getFormatDate1(date,"yyyy年MM月dd日");
    }

    public static String getFormatDate1(Date date,String formast) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formast);
        String format = dateFormat.format(date);
        return format+"";
    }

    /**
     * 根据传入的时间进行加减天数并格式化
     * @param amount
     * @param date
     * @param formast
     * @return
     */
    public static String subDate(int amount,Date date,String formast) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,amount);//把日期往后增加一天.整数往后推,负数往前移动
        date=calendar.getTime();   //这个时间就是日期往后推一天的结果
        return formatDateToString(date,formast);
    }
}
