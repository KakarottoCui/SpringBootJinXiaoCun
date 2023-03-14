package com.it.vo;

import com.it.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义类别扩展类 传参用
 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVO extends Category {
    /**
     * 当前页码
     */
    private Integer page;

    /**
     * 每页显示数量
     */
    private Integer limit;
}
