package com.example.demo.util;

/***
* sha工具类
* @author 胜利镇
* @time 2022/1/2
* @dec 
*/
public class SHAUtil {
    /**
     * sha1签名
     *
     * @param data
     * @return
     */
    public static String sha1(String data) {

        data = String.format(Constant.SIGN_SALT_FORMAT, data);

        return org.apache.commons.codec.digest.DigestUtils.sha1Hex(data);
    }
}
