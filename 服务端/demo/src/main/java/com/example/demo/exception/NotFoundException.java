package com.example.demo.exception;

import com.example.demo.util.Constant;

/***
* 资源不存在异常
* @author 胜利镇
* @time 2022/1/1
* @dec 
*/
public class NotFoundException extends CommonException{

    /***
    * 构造方法 
    */
    public NotFoundException() {
        super(Constant.ERROR_NOT_FOUND,Constant.ERROR_NOT_FOUND_MESSAGE);
    }
}
