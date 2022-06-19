package com.example.demo.domin;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class DICOMImage{
    private String patientId;
    private String uri;
    private String jpg;
    private String doctorId;
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

}
