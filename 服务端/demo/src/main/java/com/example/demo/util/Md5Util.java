package com.example.demo.util;

import org.springframework.util.DigestUtils;

/***
* md5工具类
* @author 胜利镇
* @time 2021/12/31
* @dec
*/
public class Md5Util {

    /**
     * md5签名
     * @param data
     * @return
     */
    public static String encrypt(String data) {
        return DigestUtils.md5DigestAsHex(data.getBytes());
    }
}
