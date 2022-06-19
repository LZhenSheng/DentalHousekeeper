package com.example.demo.util;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeUtil {
    /**
     * 时间+minutes分钟 是否大于当前时间
     * @param old
     * @return
     */
    public static boolean plusMinutesAfterNow(Timestamp old, long minutes) {
        //将Timestamp转为LocalDateTime
        LocalDateTime oldLocalDateTime = getLocalDateTime(old);

        //原来的时间加指定分钟
        oldLocalDateTime=oldLocalDateTime.plusMinutes(minutes);

        //当前时间
        return isAfterNow(oldLocalDateTime);
    }

    /**
     * 时间+day 是否大于当前时间
     * @param old
     * @param days
     * @return
     */
    public static boolean plusDaysAfterNow(Timestamp old, int days) {
        //将Timestamp转为LocalDateTime
        LocalDateTime oldLocalDateTime = getLocalDateTime(old);

        //原来的时间加指定天
        oldLocalDateTime=oldLocalDateTime.plusDays(days);
        return isAfterNow(oldLocalDateTime);

    }

    /**
     * 是否大于当前时间
     * @param oldLocalDateTime
     * @return
     */
    private static boolean isAfterNow(LocalDateTime oldLocalDateTime) {
        //当前时间
        LocalDateTime nowLocalDateTime = LocalDateTime.now();

        //是否在参数时间后面
        //例如：14点，在13后面
        return oldLocalDateTime.isAfter(nowLocalDateTime);
    }

    private static LocalDateTime getLocalDateTime(Timestamp old) {
        //这里使用了Java8中的日期时间api
        //将Timestamp转为LocalDateTime
        long timestamp = old.getTime();
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
