package com.example.dentalhousekeeper.domin;

import lombok.Data;
import java.sql.Timestamp;


@Data
public class Hospital extends Common{


    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号，不可以为空
     */
    private String phone;
    public String avatar;

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

}
