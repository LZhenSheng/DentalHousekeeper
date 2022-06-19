package com.example.demo.exception;

import com.example.demo.util.Constant;

/***
* 参数异常
* @author 胜利镇
* @time 2022/1/1
* @dec
*/
public class ArgumentException extends CommonException {
    /**
     * 构造方法
     */
    public ArgumentException() {
        super(Constant.ERROR_ARGUMENT, Constant.ERROR_ARGUMENT_MESSAGE);
    }
}