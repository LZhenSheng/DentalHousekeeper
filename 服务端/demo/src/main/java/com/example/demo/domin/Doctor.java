package com.example.demo.domin;

import com.example.demo.util.BCryptUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

/***
 * 医生类
 * @author 胜利镇
 * @time 2021/12/31
 * @dec
 */
@Data
public class Doctor extends Common{

    /**
     * 用户登录后token
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String sessionDigest;

    /**
     * 姓名
     */
    private String name;

    private Integer age;
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
    @Pattern(regexp = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$", message = "手机号格式不正确！")
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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String code;

    /**
     * 验证码发送时间
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Timestamp codeSentAt;

    /**
     * 加密信息
     */
    public void encryptData() {
        //加密密码
        this.password = BCryptUtil.encrypt(this.password);
    }

    private String hospital;

    private String type;

    private String room;

    private String email;

    private String description;

    @Override
    public String toString() {
        return "Doctor{" +
                "id="+getId()+
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
