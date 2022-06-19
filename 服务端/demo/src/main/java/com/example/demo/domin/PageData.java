package com.example.demo.domin;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/***
* 分页数据
* @author 胜利镇
* @time 2022/1/1
* @dec
*/

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PageData implements Serializable {
    /**
     * 当前页数
     */
    private Integer currentPage;

    /**
     * 下一页
     */
    private Long nextPage;

    /**
     * 上一页
     */
    private Long prevPage;

    /**
     * 总分页数
     */
    private Integer totalPages;

    /**
     * 总数据量
     */
    private Long totalCount;

    /**
     * 这里参数真实的列表数据
     */
    private Object data;

    public PageData(Object data) {
        this.data = data;
    }

    public PageData(Object data, Integer currentPage, Integer totalPages, Long totalCount) {
        this.data=data;
        this.currentPage=currentPage;
        this.totalPages=totalPages;
        this.totalCount=totalCount;
    }

    /**
     * 成功响应
     * @param data
     * @return
     */
    public static PageData init(Object data){
        return new PageData(data);
    }

    /**
     * 成功响应
     * @param data
     * @return
     */
    public static PageData init(Object data, Integer currentPage, Integer totalPages, Long totalCount){
        return new PageData(data, currentPage, totalPages,totalCount);
    }

}
