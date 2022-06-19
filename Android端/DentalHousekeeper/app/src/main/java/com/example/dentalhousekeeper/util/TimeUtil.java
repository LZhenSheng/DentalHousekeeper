package com.example.dentalhousekeeper.util;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期时间工具类
 */
public class TimeUtil {
    /**
     * 1分钟毫秒数
     */
    private static final long ONE_MINUTE = 60000L;

    /**
     * 1小时毫秒数
     */
    private static final long ONE_HOUR = 3600000L;

    /**
     * 1天毫秒数
     */
    private static final long ONE_DAY = 86400000L;

    /**
     * 将毫秒格式化为分:秒，例如：150:11
     *
     * @param data
     * @return
     */
    public static String ms2ms(int data) {
        if (data == 0) {
            return "00:00";
        }

        //转为秒
        data /= 1000;

        return s2ms(data);
    }

    /**
     * 将秒格式化为分:秒，例如：150:11
     *
     * @param data
     * @return
     */
    public static String s2ms(int data) {
        if (data == 0) {
            return "00:00";
        }

        //计算分钟
        int minute = data / 60;

        //秒
        int second = data - (minute * 60);

        return String.format("%02d:%02d", minute, second);
    }

    /**
     * 将分秒毫秒数据转为毫秒
     *
     * @param data 格式为：00:06.429
     * @return
     */
    public static long parseToInt(String data) {
        //将:替换成.
        data = data.replace(":", ".");

        //使用.拆分
        String[] strings = data.split("\\.");

        //分别取出分秒毫秒
        int m = Integer.parseInt(strings[0]);
        int s = Integer.parseInt(strings[1]);
        int ms = Integer.parseInt(strings[2]);

        //转为毫秒
        return (m * 60 + s) * 1000 + ms;
    }

    /**
     * 将ISO8601字符串转为项目中通用的格式
     * 几秒钟前
     * 几天前
     *
     * @param date
     * @return
     */
    public static String commonFormat(String date) {
        //将字符串转为DateTime
        DateTime dateTime = new DateTime(date);

        return commonFormat(dateTime);
    }

    /**
     * 将时间戳转为项目中通用的格式
     *
     * @param data
     * @return
     */
    public static String commonFormat(long data) {
        //解析时间戳
        DateTime dateTime = new DateTime(data);

        return commonFormat(dateTime);
    }

    /**
     * 将DateTime转为项目中通用的格式
     *
     * @param dateTime
     * @return
     */
    private static String commonFormat(DateTime dateTime) {

        //计算和现在时间的差
        //单位毫秒
        long value = new Date().getTime() - dateTime.toDate().getTime();

        if (value < 1L * ONE_MINUTE) {
            //小于1分钟

            //显示多少秒前
            long data = toSeconds(value);
            return String.format("%d秒前", data <= 0 ? 1 : data);
        } else if (value < 60 * ONE_MINUTE) {
            //小于1小时

            //显示多少分钟前
            long data = toMinutes(value);
            return String.format("%d分钟前", data);
        } else if (value < 24 * ONE_HOUR) {
            //小于1天

            //显示多少小时前
            long data = toHours(value);
            return String.format("%d小时前", data);
        } else if (value < 30 * ONE_DAY) {
            //小于1月

            //显示多少天前
            long data = toDays(value);
            return String.format("%d天前", data);
        }

        //其他时间
        //格式化为yyyyMMddHHmm
        return yyyyMMddHHmm(dateTime);
    }

    /**
     * 将DateTime转为yyyy-MM-dd HH:mm
     *
     * @param dateTime
     * @return
     */
    public static String yyyyMMddHHmm(DateTime dateTime) {
        //格式化日期
        return dateTime.toString("yyyy-MM-dd HH:mm");
    }
    /**
     * 将DateTime转为yyyy-MM-dd HH:mm
     *
     * @param dateTime
     * @return
     */
    public static String yyMMdd(DateTime dateTime) {
        //格式化日期
        return dateTime.toString("yy-MM-dd");
    }
    /**
     * 将ISO8601字符串转为yyyy-MM-dd HH:mm
     *
     * @param date
     * @return
     */
    public static String yyyyMMddHHmm(String date) {
        //将字符串转为DateTime
        DateTime dateTime = new DateTime(date);
        return yyyyMMddHHmm(dateTime);
    }


    /**
     * 将ISO8601字符串转为yyyy-MM-dd HH:mm:ss
     *
     * @param data
     * @return
     */
    public static String yyyyMMddHHmmss(String data) {
        //将字符串转为DateTime
        DateTime dateTime = new DateTime(data);

        //格式化
        return dateTime.toString("yyyy-MM-dd HH:mm:ss");
    }


    //辅助方法

    /**
     * 转为秒
     *
     * @param date
     * @return
     */
    private static long toSeconds(long date) {
        return date / 1000L;
    }

    /**
     * 转为分钟
     *
     * @param date
     * @return
     */
    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    /**
     * 转为小时
     *
     * @param date
     * @return
     */
    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    /**
     * 转为天
     *
     * @param date
     * @return
     */
    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    /**
     * 转为月
     *
     * @param date
     * @return
     */
    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    //end 辅助方法

    public static String getDate(String str){
        Date date=new Date(str);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date); //2013-01-14
    }

    public static String getDate(){
        Date date=new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
    public static String getDate(Date date,long day) throws Exception {
        long time = date.getTime();
        day = day*24*60*60*1000;
        time+=day;
        Date dte=new Date(time);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(dte);
    }
}
