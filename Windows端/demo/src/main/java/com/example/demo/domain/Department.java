package com.example.demo.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Department {
    private String name;
    private String profile;
    private String hospital;
    /**
     * 数据id
     */
    public String id;

    /**
     * 创建时间
     */
    public Timestamp created_at;

    /**
     * 更新时间
     */
    public Timestamp updated_at;
}
