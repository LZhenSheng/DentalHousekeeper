package com.example.demo.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Payment {

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

    public String patient;
    public String hospital;
    public String doctor;
    public String preorder;
    public String appointment;
    private double money;
}
