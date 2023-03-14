package com.it.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * layui 数据表格类
 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataGridViewResult {

    /**
     * 执行状态码
     */
    private Integer code=0;
    /**
     * 提示信息
     */
    private String msg="";
    /**
     * 分页数据数量
     */
    private Long count=0L;
    /**
     * 数据列表
     */
    private Object data;

    /**
     *
     * @param count 数据总数量
     * @param data  数据列表
     */
    public DataGridViewResult(Long count, Object data) {
        super();
        this.count = count;
        this.data = data;
    }
    public DataGridViewResult(Object data) {
        super();
        this.data = data;
    }
}
