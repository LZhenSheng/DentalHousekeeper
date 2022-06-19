package com.example.demo.domain;

import lombok.Data;
import java.sql.Timestamp;

/***
* 病人类
* @author 胜利镇
* @time 2021/12/31
* @dec 
*/
@Data
public class Patient{
    /**
     * 数据id
     */
    public String id;
    public int age;
    /**
     * 创建时间
     */
    public Timestamp created_at;

    /**
     * 更新时间
     */
    public Timestamp updated_at;
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

}
