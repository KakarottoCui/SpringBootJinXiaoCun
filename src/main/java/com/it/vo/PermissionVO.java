package com.it.vo;

import com.it.entity.Permission;
import lombok.Data;

/**
 * 自定义分页扩展类 传参数用的
 */

@Data
public class PermissionVO extends Permission {

    /**
     * 当前页码
     */
    private Integer page;

    /**
     * 每页显示数量
     */
    private Integer limit;

}
