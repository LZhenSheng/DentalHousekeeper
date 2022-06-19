package com.example.demo.domin;

import lombok.Data;

@Data
public class AppointMent extends Common{

    private String doctorId;
    private String patientId;
    private String preorderId;
    private String no;
    private String uri;
    private boolean status;


}