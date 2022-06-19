package com.example.demo.exception;

import java.util.List;

/**
 * 全局自定义异常
 * 目的是对错误进行封装
 * 以方便全局出错
 */
public class CommonException extends RuntimeException {
    /**
     * 构造方法
     */
    public CommonException() {
    }

    /**
     * 构造方法
     * @param status
     * @param message
     */
    public CommonException(int status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public CommonException(int status, String message,Throwable cause) {
        super(cause);
        this.status = status;
        this.message = message;
    }

    /**
     * 构造方法
     * @param status
     * @param message
     * @param detail
     */
    public CommonException(int status, String message,List<String> detail) {
        super(message);
        this.status = status;
        this.message = message;
        this.detail = detail;
    }

    /**
     * 错误码
     */
    private int status;

    /**
     * 错误详细信息
     */
    private String message;

    /**
     * 详细错误信息
     */
    private List<String> detail;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getDetail() {
        return detail;
    }

    public void setDetail(List<String> detail) {
        this.detail = detail;
    }


}
