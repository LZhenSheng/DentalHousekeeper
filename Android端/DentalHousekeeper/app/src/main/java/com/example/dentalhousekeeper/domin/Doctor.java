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
public class Doctor extends Common {

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
    private Integer gender;

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

    public Doctor(String phone) {
        this.phone = phone;
    }

    public Doctor(){}
    public Doctor(String name, String phone, String password) {
        this.name = name;
        this.phone = phone;
        this.password = password;
    }

    public Doctor(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    private String hospital;

    public Doctor(String id,String name, Integer gender, String province, String city, String area,Integer age,String birthday,String avatar,String email,String description) {
        setId(id);
        this.name = name;
        this.gender = gender;
        this.province = province;
        this.city = city;
        this.area = area;
        this.age=age;
        this.birthday=birthday;
        this.avatar=avatar;
        this.email=email;
        this.description=description;
    }

    private Integer age;
    private String type;

    private String room;

    private String email;

    private String description;

}
