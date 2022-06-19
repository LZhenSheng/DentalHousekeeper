package com.example.dentalhousekeeper.domin;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 客户端支付参数
 */
@Data
public class Pay {
    /**
     * 微信支付参数
     */
    private Map<String, String> wechatPay;

    /**
     * 支付渠道
     */
    private int channel;

    /**
     * 支付宝支付
     */
    private String pay;

    /**
     * 构造方法
     *
     * @param channel
     * @param pay
     */
    public Pay(int channel, String pay) {
        this.channel = channel;
        this.pay = pay;
    }

    /**
     * 构造方法
     * @param channel
     * @param data
     */
    public Pay(Integer channel, Map<String, String> data) {
        this.channel = channel;
        this.wechatPay = data;
    }
}
