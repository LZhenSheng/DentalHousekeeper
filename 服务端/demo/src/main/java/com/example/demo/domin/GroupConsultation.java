package com.example.demo.domin;

import lombok.Data;
import org.bouncycastle.util.Times;

import java.sql.Timestamp;

@Data
public class GroupConsultation {

    private String id;
    private String date;
    private String start;
    private String reback;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
