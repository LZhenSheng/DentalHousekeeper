package com.example.demo.domin;

import com.example.demo.util.Constant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.awt.print.Book;

/**
 * 订单模型
 */
@Data
public class Order extends Common {
    /**
     * 订单状态：0:待支付,10:支付完成,20:订单关闭
     */
    private int status;

    /**
     * 价格
     * <p>
     * 真实项目中可能还有现付价格
     * 由于有优惠券等功能
     */
    private double price;

    /**
     * 订单来源
     * <p>
     * 取值参见常量文件
     */
    @NotNull(message = "订单来源不能为空")
    private Integer source;

    /**
     * 支付来源
     * <p>
     * 取值参见常量文件
     */
    private Integer origin;

    /**
     * 支付渠道
     * <p>
     * 取值参见常量文件
     */
    private Integer channel;

    /**
     * 订单号
     */
    private String number;

    /**
     * 第三方订单号
     * <p>
     * 如果是支付宝支付，就是支付宝那边的订单号
     * 如果是微信支付，就是微信那个的订单号
     */
    private String other;

    /**
     * 商品id
     */
    private String bookId;

    /**
     * 商品
     */
    private Book book;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户对象
     */
    private User user;

    /**
     * 是否已经支付成功
     *
     * @return
     */
    @JsonIgnore
    public boolean isBuy() {
        return status == Constant.ORDER_STATUS_PAID;
    }

    /**
     * 是否未支付
     *
     * @return
     */
    @JsonIgnore
    public boolean unpaid() {
        return status == Constant.ORDER_STATUS_UNPAID;
    }
}
