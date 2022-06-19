package com.example.dentalhousekeeper.domin;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class DICOMImage {
    private String patientId;
    private String doctorId;
    private String uri;
    private String jpg;
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
