package com.example.dentalhousekeeper.util;

import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.MessageContent;
import cn.jpush.im.android.api.content.TextContent;

/**
 * 消息工具类
 */
public class MessageUtil {
    /**
     * 获取聊天内容
     *
     * @param data
     * @return
     */
    public static String getContent(MessageContent data) {
        if (data instanceof ImageContent) {
            //图片消息
            return "[图片]";
        } else if (data instanceof TextContent) {
            //文本消息
            return ((TextContent) data).getText();
        }

        return "[其他消息]";
    }
}
