package com.example.demo.util;



import com.example.demo.domin.Common;
import com.example.demo.domin.PageData;
import com.example.demo.domin.Response;
import com.example.demo.exception.NotFoundException;

import java.util.List;

import static com.example.demo.util.Constant.ERROR_UNKNOWN;
import static com.example.demo.util.Constant.ERROR_UNKNOWN_MESSAGE;

/***
* 响应工具类
* @author 胜利镇
* @time 2022/1/1
* @dec 主要对象返回的数据进行处理
* 例如：加密；包装
*/
public class ResponseUtil {

    /**
     * 成功响应
     * 没有数据
     * @return
     */
    public static Response wrap(){
        return new Response();
    }


    /**
     * 根据id初始化
     * @param id
     * @return
     */
    public static Object wrap(String id) {
        Common data = new Common();
        data.setId(id);
        return wrap(data);
    }

    /**
     * 可能成功响应
     * @param data
     * @return
     */
    public static Object wrap(Object data) {
        if(data==null){
            //如果资源为空，抛出资源不存在异常
            throw  new NotFoundException();
        }
        if (data instanceof List) {
            //没有分页包裹

            //就对列表包裹
            data = PageData.init(data);
        }

        data = new Response(data);
        return data;
    }

    /**
     * 错误响应
     * @param status
     * @param message
     * @return
     */
    public static Response failed(int status, String message) {
        return new Response(status, message);
    }

    /**
     * 错误响应
     * @param status
     * @param message
     * @return
     */
    public static Response failed(int status, String message, List<String> detail) {
        return new Response(status, message, detail);
    }

    /**
     * 未知错误响应
     * @return
     */
    public static Response failed() {
        return new Response(ERROR_UNKNOWN, ERROR_UNKNOWN_MESSAGE);
    }

}
