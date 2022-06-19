package com.example.demo.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AppointMent{
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
    private String doctor_id;
    private String patient_id;
    private String preorder_id;
    private String no;
    private String uri;
    private boolean status;

    @Override
    public String toString() {
        return "AppointMent{" +
                "doctor_id='" + doctor_id + '\'' +
                ", patient_id='" + patient_id + '\'' +
                ", preorder_id='" + preorder_id + '\'' +
                ", no='" + no + '\'' +
                ", uri='" + uri + '\'' +
                ", status=" + status +
                ", id='" + id + '\'' +
                ", createdAt=" + created_at +
                ", updatedAt=" + updated_at +
                '}';
    }
}