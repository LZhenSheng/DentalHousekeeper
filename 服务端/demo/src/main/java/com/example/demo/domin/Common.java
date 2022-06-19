package com.example.demo.domin;

import lombok.Data;

import java.sql.Timestamp;

/***
* 与业务相关的通用模型
* @author 胜利镇
* @time 2021/12/31
* @dec
*/
@Data
public class Common extends Base{
    /**
     * 数据id
     */
    private String id;

    /**
     * 创建时间
     */
    private Timestamp createdAt;

    /**
     * 更新时间
     */
    private Timestamp updatedAt;

    /**
     * 构造方法
     */
    public Common() {
    }

    /**
     * 构造方法
     *
     * @param id
     */
    public Common(String id) {
        this.id = id;
    }

    /**
     * 构造方法
     * @param id
     * @param createdAt
     * @param updatedAt
     */
    public Common(String id, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
