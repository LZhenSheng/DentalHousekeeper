package com.example.dentalhousekeeper.domin;


import java.sql.Timestamp;

import lombok.Data;

@Data
public class AppointMent {

    private String preorder_id;
    private String doctor_id;
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

    private String uri;
    private String no;
    private boolean status;
}
