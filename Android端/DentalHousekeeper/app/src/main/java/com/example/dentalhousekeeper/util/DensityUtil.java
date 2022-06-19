package com.example.dentalhousekeeper.util;

import android.content.Context;

/**
 * Android尺寸相关工具栏
 */
public class DensityUtil {
    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     *
     * @param context
     * @param data
     * @return
     */
    public static int dip2px(Context context, float data) {
        //获取手机的缩放
        float scale = context.getResources().getDisplayMetrics().density;

        //px=缩放*dp
        return (int) (data * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float data) {
        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (data / scale + 0.5f);
    }
}
