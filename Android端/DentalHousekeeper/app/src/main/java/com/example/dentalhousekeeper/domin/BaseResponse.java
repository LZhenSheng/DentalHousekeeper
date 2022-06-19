package com.example.dentalhousekeeper.domin;

import lombok.Data;

/**
 * 通用网络请求响应模型
 */
@Data
public class BaseResponse {
    /**
     * 状态码
     * 只有发生了错误才会有值
     */
    private int status;

    /**
     * 出错的提示信息
     * 发生了错误不一定有
     */
    private String message;
}
