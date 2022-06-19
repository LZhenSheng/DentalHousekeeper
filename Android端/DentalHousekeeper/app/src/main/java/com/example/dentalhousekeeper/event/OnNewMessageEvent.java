package com.example.dentalhousekeeper.event;

import cn.jpush.im.android.api.model.Message;

/**
 * 新消息事件
 */
public class OnNewMessageEvent {
    /**
     * 消息
     */
    private Message data;

    public OnNewMessageEvent(Message data) {
        this.data = data;
    }

    public Message getData() {
        return data;
    }

    public void setData(Message data) {
        this.data = data;
    }
}


