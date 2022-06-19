package com.example.demo.domain;

import lombok.Data;
import java.sql.Timestamp;

/***
 * 医生类
 * @author 胜利镇
 * @time 2021/12/31
 * @dec
 */
@Data
public class Doctor extends Common{


    private String sessionDigest;

    private String name;

    private String avatar;

    private Integer gender;
    private Integer age;
    private String birthday;

    private String phone;

    private String province;

    private String city;

    private String area;
    private String password;

    private String code;

    private Timestamp codeSentAt;

    private String hospital;

    private String type;

    private String room;

    private String email;

    private String description;

    @Override
    public String toString() {
        return "Doctor{" +
                "id="+id+
                "sessionDigest='" + sessionDigest + '\'' +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", gender=" + gender +
                ", birthday='" + birthday + '\'' +
                ", phone='" + phone + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", password='" + password + '\'' +
                ", code='" + code + '\'' +
                ", codeSentAt=" + codeSentAt +
                ", hospital='" + hospital + '\'' +
                ", type='" + type + '\'' +
                ", room='" + room + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
