package com.example.demo.domin;

import com.example.demo.util.BCryptUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

@Data
public class Hospital extends Common{


    /**
     * 姓名
     */
    private String name;
    private String avatar;

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

    private String pid;
    /**
     * 加密信息
     */
    public void encryptData() {
        //加密密码
        this.password = BCryptUtil.encrypt(this.password);
    }
}
