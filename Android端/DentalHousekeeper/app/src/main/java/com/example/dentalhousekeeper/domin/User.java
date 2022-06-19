package com.example.dentalhousekeeper.domin;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class User {

    private String name;
    private String avatar;
    private Integer gender;
    private String phone;
    private String province;
    private String city;
    private String area;
    public User(){

    }

    public User(Patient data){
        name=data.getName();
        avatar=data.getAvatar();
        gender=data.getGender();
        phone=data.getPhone();
        province=data.getProvince();
        city=data.getCity();
        area=data.getArea();
    }

    public User(Doctor data){
        name=data.getName();
        avatar=data.getAvatar();
        gender=data.getGender();
        phone=data.getPhone();
        province=data.getProvince();
        city=data.getCity();
        area=data.getArea();
    }
}
