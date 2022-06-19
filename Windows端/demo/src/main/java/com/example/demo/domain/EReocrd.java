package com.example.demo.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class EReocrd{
    private String doctor;
    private String patient_id;
    private String appointment_id;
    private String cheif_complaint;
    private String present_past_history;
    private String past_history;
    private String inspect;
    private String supplementary_examination;
    private String diagnosis;
    private String treatment_plan;
    private String medical_care;
    private String medical_advice;
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