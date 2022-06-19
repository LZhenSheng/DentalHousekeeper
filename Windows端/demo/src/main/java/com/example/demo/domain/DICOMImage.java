package com.example.demo.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class DICOMImage {
    private String patientId;
    private String uri;
    private String jpg;
    /**
     * 数据id
     */
    public String id;

    /**
     * 创建时间
     */
    public Timestamp createdAt;

    /**
     * 更新时间
     */
    public Timestamp updatedAt;
}
