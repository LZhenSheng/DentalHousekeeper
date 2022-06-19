package com.example.demo.util;

/**
 * 随机工具类
 */
public class RandomUtil {

    /**
     * 随机产生4位整数
     * @return
     */
    public static int int4() {
        return (int)((Math.random()*9+1)*1000);
    }
}
