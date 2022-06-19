package com.example.dentalhousekeeper.domin;

import lombok.Data;

/**
 * 详情网络请求解析类
 * <p>
 * 继承BaseResponse
 * 定义了一个泛型T
 */
@Data
public class DetailResponse<T> extends BaseResponse {
    /**
     * 真实数据
     * 类似是泛型
     */
    private T data;
}
