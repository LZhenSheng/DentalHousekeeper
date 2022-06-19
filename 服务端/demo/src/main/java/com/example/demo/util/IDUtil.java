package com.example.demo.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * id工具类
 */
public class IDUtil {
    /**
     * 获取uuid
     *
     * @return
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();

        //去掉-
        return uuid.replace("-", "");
    }

    /**
     * 生成订单id
     *
     * 订单号 18位
     * 时间戳14位，精确到秒
     * 随机数4位
     * @return
     */
    public static String getOrderNumber() {
        //时间戳
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        //当前时间
        LocalDateTime nowLocalDateTime = LocalDateTime.now();

        //格式化
        String timeString = nowLocalDateTime.format(dateTimeFormatter);

        //产生1000-到10000之间的随机数，不包括10000
        String randString = String.valueOf(RandomUtil.int4());

        //格式化
        return String.format("%s%s",timeString,randString);
    }
}