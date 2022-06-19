package com.example.demo.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/***
* BCrypt工具类
* @author 胜利镇
* @time 2022/1/2
* @dec 
*/
public class BCryptUtil {
    /**
     * 格式化密码加盐
     *
     * @param data
     * @return
     */
    public static String formatPassword(String data) {
        return String.format(Constant.PASSWORD_SALT_FORMAT, data);
    }

    /**
     * 使用BCrypt加密
     *
     * @param data
     * @return
     */
    public static String encrypt(String data) {

        //格式化密码
        data = formatPassword(data);

        //加密
        BCryptPasswordEncoder encoder = getPasswordEncoder();

        return encoder.encode(data);
    }

    /**
     * 判断BCrypt加密的数据是否匹配
     *
     * @param rawPassword     未加密的数据
     * @param encodedPassword 加密后的数据
     * @return
     */
    public static boolean matchEncode(String rawPassword, String encodedPassword) {
        //格式化密码
        //主要是添加盐
        rawPassword = formatPassword(rawPassword);

        //判断是否匹配
        return getPasswordEncoder().matches(rawPassword, encodedPassword);
    }

    /**
     * 获取密码加密器
     *
     * @return
     */
    private static BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
