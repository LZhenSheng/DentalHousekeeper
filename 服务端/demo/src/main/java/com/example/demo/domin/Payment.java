package com.example.demo.domin;

import lombok.Data;

@Data
public class Payment extends Common{
    private String patient;
    private String doctor;
    private String hospital;
    private String preorder;
    private String appointment;
    private double money;
}
