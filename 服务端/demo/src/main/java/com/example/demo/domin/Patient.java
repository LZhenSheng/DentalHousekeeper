package com.example.demo.domin;

import com.example.demo.util.BCryptUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

/***
* 病人类
* @author 胜利镇
* @time 2021/12/31
* @dec 
*/
@Data
public class Patient extends Common{

    private String card;
    /**
     * 用户登录后token
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String sessionDigest;

    /**
     * 姓名
     */
    @NotEmpty(message = "昵称不能为空")
    @Length(min = 1, max = 15, message = "昵称长度必须为1~15位")
    private String name;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别，不能为空，默认为0：保密，1：男，2：女
     */
    private Integer gender;

    private Integer age;
    /**
     * 出生日期
     * yyyy-MM-dd格式
     */
    private String birthday;

    /**
     * 手机号，不可以为空
     */
    @NotEmpty(message = "手机号不能为空！")
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
    @NotEmpty(message = "密码不能为空")
    @Length(min = 6, max = 15, message = "密码长度必须为6~15位")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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

    @Override
    public String toString() {
        return "Patient{" +
                "id='"+getId()+"\'"+
                "card='" + card + '\'' +
                ", sessionDigest='" + sessionDigest + '\'' +
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
                '}';
    }
}
