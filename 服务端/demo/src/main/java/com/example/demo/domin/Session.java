package com.example.demo.domin;

import lombok.Data;

/**
 * 用户登录后session（token）模型
 * 这里的session和web的session不一样
 * 只是我们取这个类名而已
 */
@Data
public class Session {
    /**
     * 用户id
     */
    private String user;

    /**
     * 登录后的token
     */
    private String session;

}
