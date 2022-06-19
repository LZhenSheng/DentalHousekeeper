package com.example.demo.util;

/**
 * 全局常量类
 */
public class Constant {

    //全局错误码
    /**
     * 未知错误
     */
    public static final int ERROR_UNKNOWN = 20;
    public static final String ERROR_UNKNOWN_MESSAGE = "未知错误，请稍后再试！";

    /**
     * 参数错误
     */
    public static final int ERROR_ARGUMENT = 30;
    public static final String ERROR_ARGUMENT_MESSAGE = "参数错误！";

    /**
     * 资源不存在
     */
    public static final int ERROR_NOT_FOUND = 40;
    public static final String ERROR_NOT_FOUND_MESSAGE = "资源不存在！";

    /**
     * 用户已经存在
     */
    public static final int ERROR_USER_EXIST = 1000;
    public static final String ERROR_USER_EXIST_MESSAGE = "用户已经存在！";

    /**
     * 用户不存在
     */
    public static final int ERROR_USER_NOT_EXIST = 1010;
    public static final String ERROR_USER_NOT_EXIST_MESSAGE = "用户不存在！";

    /**
     * 用户名或密码错误！
     */
    public static final int ERROR_USERNAME_OR_PASSWORD = 1030;
    public static final String ERROR_USERNAME_OR_PASSWORD_MESSAGE = "用户名或密码错误！";

    /**
     * 保存数据失败
     */
    public static final int ERROR_SAVE_DATA = 1540;
    public static final String ERROR_SAVE_DATA_MESSAGE = "保存数据失败，请稍后再试！";

    /**
     * 数据已存在
     */
    public static final int ERROR_DATA_EXIST = 1550;
    public static final String ERROR_DATA_EXIST_MESSAGE = "数据已存在！";

    /**
     * 密码加盐格式化
     */
    public static final String PASSWORD_SALT_FORMAT = "wt5jKURZtH6RDtt%suWg7xnE4Mr5Xwzm";

    /**
     * 签名算法字符串
     * %%转义%
     */
    public static final String SIGN_SALT_FORMAT = "wyZlmvYHoS^UU7#q%skNPd#3NRB%%84A!CF";

    /**
     * 用户id header名称
     */
    public static final String USER = "User";

    /**
     * 用户认证token header名称
     */
    public static final String AUTHORIZATION = "Authorization";

    /**
     * request保存当前用户字段
     */
    public static final String CURRENT_USER = "currentUser";

    /**
     * 数据库操作成功，返回1，表示成功
     */
    public static final int RESULT_OK = 1;

    //订单状态
    /**
     * 待支付
     */
    public static final int ORDER_STATUS_UNPAID = 0;

    /**
     * 支付完成
     */
    public static final int ORDER_STATUS_PAID = 10;

    /**
     * 支付宝，回调后服务器处理完毕后
     * 返回该字符串
     * 不然支付宝会以一定的频率继续回调接口
     */
    public static final String PAY_SUCCESS = "success";

}
