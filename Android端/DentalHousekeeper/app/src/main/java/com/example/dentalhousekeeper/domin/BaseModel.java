package com.example.dentalhousekeeper.domin;

import java.io.Serializable;

import lombok.Data;

/**
 * 所有模型父类
 */
@Data
public class BaseModel implements Serializable {
    /**
     * Id
     */
    private String id;

    /**
     * 创建时间
     */
    private String created_at;

    /**
     * 创建时间
     */
    private String updated_at;
}
