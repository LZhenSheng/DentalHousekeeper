package com.example.demo.domain;

import lombok.Data;

@Data
public class PreOrder extends Common{

    private String doctor_id;
    private String date;
    private String start_time;
    private String end_time;
    private int number;
    private double money;
    private String style;
}