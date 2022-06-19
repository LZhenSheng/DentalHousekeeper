package com.example.dentalhousekeeper.util;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.StringRes;

import es.dmoral.toasty.Toasty;


/**
 * Toast工具类
 */
public class ToastUtil {

    /**
     * 上下文
     */
    private static Context context;

    /**
     * 初始化方法
     *
     * @param context
     */
    public static void init(Context context) {
        ToastUtil.context = context;
    }

    /**
     * 显示短时间错误toast
     *
     * @param id
     */
    public static void errorShortToast(@StringRes int id) {
        Toasty.error(context, id, Toasty.LENGTH_SHORT).show();
    }

    /**
     * 显示短时间错误toast
     *
     * @param message
     */
    public static void errorShortToast(String message) {
        Toasty.error(context, message, Toasty.LENGTH_SHORT).show();
    }

    /**
     * 显示长时间错误toast
     *
     * @param id
     */
    public static void errorLongToast(@StringRes int id) {
        Toasty.error(context, id, Toasty.LENGTH_LONG).show();
    }

    /**
     * 显示短时间正确toast
     *
     * @param id
     */
    public static void successShortToast(@StringRes int id) {
        Toasty.success(context, id, Toasty.LENGTH_SHORT).show();
    }

    /**
     * 显示短时间正确toast
     *
     * @param data
     */
    public static void successShortToast(String data) {
        Toasty.success(context, data, Toasty.LENGTH_SHORT).show();
    }

    /**
     * 显示短时间正确toast
     *
     * @param data
     */
    public static void successLongToast(String data) {
        Toasty.success(context, data, Toasty.LENGTH_LONG).show();
    }

    /**
     * 信息Toast：
     */
    public static void infoShortToast(String data) {
        Toasty.info(context, data, Toast.LENGTH_SHORT, true).show();
    }

    /**
     * 警告Toast：
     */
    public static void warningShortToast(String data) {
        Toasty.warning(context, data, Toast.LENGTH_SHORT, true).show();
    }

    /***
     *通常的Toast
     */
    public static void normalShortToast(String data) {
        Toasty.normal(context, data, Toast.LENGTH_SHORT).show();
    }

    /**
     * 带有图标的常用Toast：
     * 没有效果，感兴趣自己尝试
     */
//    public static void iconShowToast(String data, int picture) {
//        Toasty.normal(context, data, picture).show();
//    }

    /**
     * 创建自定义Toasts
     * @param data
     * @param picture 图片id
     * @param color 背景颜色
     */
    public static void customShowToast(String data,int picture,int color) {
        Toasty.custom(context, data, picture,color , Toast.LENGTH_SHORT, true, true).show();
    }
}
