package com.example.demo.domain;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class Hospital extends Common{


    /**
     * 姓名
     */
    public String name;

    /**
     * 手机号，不可以为空
     */
    public String phone;
    public String avatar;
    /**
     * 省
     */
    public String province;

    /**
     * 市
     */
    public String city;

    /**
     * 区
     */
    public String area;

    /**
     * 密码，encrypt方法加密
     */
    public String password;

    /**
     * 验证码字段,md5签名
     */
    public String code;

    /**
     * 验证码发送时间
     */
    public Timestamp codeSentAt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Timestamp getCodeSentAt() {
        return codeSentAt;
    }

    public void setCodeSentAt(Timestamp codeSentAt) {
        this.codeSentAt = codeSentAt;
    }
}
