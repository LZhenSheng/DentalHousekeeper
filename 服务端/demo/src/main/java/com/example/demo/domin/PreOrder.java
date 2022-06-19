package com.example.demo.domin;

import lombok.Data;

@Data
public class PreOrder extends Common{

    private String doctorId;
    private String date;
    private String startTime;
    private String endTime;
    private int number;
    private double money;
    private int rest;
    private String style;
    private String hospital;
}