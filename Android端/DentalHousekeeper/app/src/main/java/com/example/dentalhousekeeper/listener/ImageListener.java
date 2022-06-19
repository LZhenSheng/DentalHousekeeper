package com.example.dentalhousekeeper.listener;

import android.widget.ImageView;

/**
 * 动态监听器
 */
public interface ImageListener {
    /**
     * 点击了动态图片回调
     *
     * @param rv
     * @param imageUris
     * @param index
     */
    void onImageClick(ImageView rv, String imageUris, int index);
}