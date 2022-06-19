package com.example.dentalhousekeeper.domin;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class EReocrd{
    private String doctor;
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
    private String patient_id;
    /**
     * 数据id
     */
    private String id;

    /**
     * 创建时间
     */
    private Timestamp created_at;

    /**
     * 更新时间
     */
    private Timestamp updated_at;
}
