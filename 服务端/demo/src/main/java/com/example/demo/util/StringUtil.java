package com.example.demo.util;

/**
 * 字符串工具类
 */
public class StringUtil {
    /**
     * 保留2位小数
     *
     * @param data
     * @return
     */
    public static String formatFloat2(double data) {
        return String.format("%.2f", data);
    }
}
