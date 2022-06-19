package com.example.dentalhousekeeper.event;

import com.example.dentalhousekeeper.domin.PayResult;

/**
 * 支付宝支付状态改变了事件
 */
public class OnAlipayStatusChangedEvent {
    /**
     * 支付状态
     */
    private PayResult data;

    public OnAlipayStatusChangedEvent(PayResult data) {
        this.data = data;
    }

    public PayResult getData() {
        return data;
    }

    public void setData(PayResult data) {
        this.data = data;
    }
}
