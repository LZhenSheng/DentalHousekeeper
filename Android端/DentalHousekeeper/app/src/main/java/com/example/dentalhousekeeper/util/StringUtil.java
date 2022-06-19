package com.example.dentalhousekeeper.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串相关工具类
 */
public class StringUtil {

    /**
     * 是否是手机号
     *
     * @param value
     * @return
     */
    public static boolean isPhone(String value) {
        if(value==null)
            return false;
        return value.matches(Constant.REGEX_PHONE);
    }


    /**
     * 是否符合密码格式
     *
     * @param value
     * @return
     */
    public static boolean isPassword(String value) {
        if(value==null)
            return false;
        return value.length() >= 6 && value.length() <= 15;
    }

    /**
     * 是否符合昵称格式
     *
     * @param value
     * @return
     */
    public static boolean isNickname(String value) {
        return value.length() >= 2 && value.length() <= 10;
    }

    /**
     * 是否符合验证码格式
     *
     * @param value
     * @return
     */
    public static boolean isCode(String value) {
        return value.length() == 4;
    }


    /**
     * 格式化消息数量
     *
     * @param data
     * @return
     */
    public static String formatMessageCount(int data) {
        if (data > 99) {
            return "99+";
        }

        return String.valueOf(data);
    }

    /***
     * 判断手机号和密码是否符合格式
     * @param account
     * @param password
     * @return
     */
    public static boolean isAccountPassword(String account,String password) {
        return isPhone(account)&&isPassword(password);
    }

    public static boolean isEmpty(String data) {
        return StringUtils.isEmpty(data);
    }
}
