package com.example.dentalhousekeeper.domin;

import java.sql.Timestamp;

import lombok.Data;

/***
* 病人类
* @author 胜利镇
* @time 2021/12/31
* @dec 
*/
@Data
public class Patient extends Common {

    private String card;
    /**
     * 用户登录后token
     */
    private String sessionDigest;

    /**
     * 姓名
     */
    private String name;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别，不能为空，默认为0：保密，1：男，2：女
     */
    private int gender;

    private int age;
    /**
     * 出生日期
     * yyyy-MM-dd格式
     */
    private String birthday;

    /**
     * 手机号，不可以为空
     */
    private String phone;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 密码，encrypt方法加密
     */
    private String password;

    /**
     * 验证码字段,md5签名
     */
    private String code;

    /**
     * 验证码发送时间
     */
    private Timestamp codeSentAt;

    public Patient(String phone) {
        this.phone = phone;
    }

    public Patient(String name, String phone, String password) {
        this.name = name;
        this.phone = phone;
        this.password = password;
    }

    public Patient(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public Patient(String id,String name, Integer gender, String province, String city, String area,String card,Integer age,String birthday,String avatar) {
        setId(id);
        this.name = name;
        this.gender = gender;
        this.province = province;
        this.city = city;
        this.area = area;
        this.card=card;
        this.age=age;
        this.avatar=avatar;
        this.birthday=birthday;
    }

    public Patient(){}
}
