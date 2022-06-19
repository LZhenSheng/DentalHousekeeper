package com.example.demo.domin;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;

/***
* 所有模型基类
* @author 胜利镇
* @time 2021/12/31
* @dec
*/
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Base implements Serializable {
}
