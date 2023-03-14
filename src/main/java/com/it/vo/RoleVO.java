package com.it.vo;

import com.it.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义角色扩展类 传参用
 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVO extends Role {
    /**
     * 当前页码
     */
    private Integer page;

    /**
     * 每页显示数量
     */
    private Integer limit;
}
