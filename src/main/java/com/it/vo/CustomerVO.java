package com.it.vo;

import com.it.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义顾客扩展类 传参用
 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerVO extends Customer {
    /**
     * 当前页码
     */
    private Integer page;

    /**
     * 每页显示数量
     */
    private Integer limit;
}
