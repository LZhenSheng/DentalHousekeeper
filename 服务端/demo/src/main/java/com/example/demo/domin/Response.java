package com.example.demo.domin;

import lombok.Data;

import java.util.List;

/***
* 所有接口返回包装类
* @author 胜利镇
* @time 2022/1/1
* @dec
*/
@Data
public class Response {

    /**
     * 状态码
     * 默认为0：表示成功
     */
    private int status;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 详细错误信息
     */
    private List<String> detail;

    /**
     * 真实数据
     * 详情接口就是一个对象
     * 对于列表接口是一个Page对象
     */
    private Object data;

    public Response() {
    }

    /**
     * 构造方法
     * @param data
     */
    public Response(Object data) {
        this.data = data;
    }

    /**
     * 构造方法
     * @param status
     * @param message
     */
    public Response(int status, String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * 构造方法
     * @param status
     * @param message
     * @param data
     */
    public Response(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    /**
     * 构造方法
     * @param status
     * @param message
     * @param detail
     */
    public Response(int status, String message, List<String> detail) {
        this.status = status;
        this.message = message;
        this.detail = detail;
    }
}
