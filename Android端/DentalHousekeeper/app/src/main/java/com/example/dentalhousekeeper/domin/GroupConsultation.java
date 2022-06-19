package com.example.dentalhousekeeper.domin;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class GroupConsultation {
    private String id;
    private String date;
    private String start;
    private String reback;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
