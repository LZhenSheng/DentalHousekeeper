package com.example.demo.domin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

/**
 * 用户对象
 */
@Data
public class User extends Common {
    /**
     * 昵称，不能为空
     * <p>
     * 长度为1~30
     *
     * @NotEmpty :不能为null，且Size>0
     * @NotNull:不能为null，但可以为empty,没有Size的约束
     * @NotBlank:只用于String,不能为null且trim()之后size>0
     */
    @NotBlank(message = "昵称不能为空")
    @Length(min = 1, max = 30, message = "昵称长度必须为1~30位")
//    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 描述
     */
//    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String description;

    /**
     * 性别
     * <p>
     * 不能为空
     * 默认为0：保密；1：男；2：女
     */
    private Integer gender;

    /**
     * 出生日志
     * <p>
     * 格式为yyyy-MM-dd格式
     * 真实项目中建议使用日期格式
     */
    private String birthday;

    /**
     * 邮箱
     * <p>
     * 不能为空
     */
    @NotBlank(message = "邮箱不能为空")
    @Pattern(regexp = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$", message = "邮箱格式不正确")
    private String email;

    /**
     * 手机号
     * <p>
     * 不可以为空
     * 手机号正则表达式
     * 移动：134 135 136 137 138 139 147 150 151 152 157 158 159 178 182 183 184 187 188 198
     * 联通：130 131 132 145 155 156 166 171 175 176 185 186
     * 电信：133 149 153 173 177 180 181 189 199
     * 虚拟运营商: 170
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$", message = "手机号格式不正确")
    private String phone;

    /**
     * 省
     */
    private String province;

    /**
     * 省编码
     */
    private String provinceCode;

    /**
     * 市
     */
    private String city;

    /**
     * 市编码
     */
    private String cityCode;

    /**
     * 区
     */
    private String area;

    /**
     * 区编码
     */
    private String areaCode;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 15, message = "密码长度必须为6~15位")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * 用户登录后的token
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String sessionDigest;

    /**
     * 邮箱确认token
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String confirmationDigest;

    /**
     * 邮箱确认时间
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Timestamp confirmedAt;

    /**
     * 邮箱确认链接发送时间
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Timestamp confirmationSendAt;

    /**
     * 第三方登录QQid，使用sha1算法计算签名
     */
    private String qqId;

    /**
     * 第三方登录QQid加密后值，bcrypt算法加密
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String qqIdDigest;

    /**
     * 第三方登录微信id，使用sha1算法计算签名
     */
    private String wechatId;

    /**
     * 第三方登录微信id加密后值，bcrypt算法加密
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String wechatIdDigest;

    /**
     * 第三方登录微博id，使用sha1算法计算签名
     */
    private String weiboId;

    /**
     * 第三方登录微博id加密后值，bcrypt算法加密
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String weiboIdDigest;

    /**
     * 验证码字段
     * <p>
     * md5签名
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String code;

    /**
     * 验证码发送时间
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Timestamp codeSentAt;

    /**
     * 有值就表示关注了
     */
    private Integer following;

    /**
     * 推送字段
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String push;

    /**
     * 邮箱是否已经验证了
     * <p>
     * 邮箱确认时间存在表示验证了
     *
     * @return
     */
    @JsonIgnore
    public boolean isEmailVerification() {
        return confirmedAt != null;
    }
}
