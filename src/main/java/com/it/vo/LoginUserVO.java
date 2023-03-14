package com.it.vo;


import com.it.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * 登录用户类
 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserVO {

    /**
     * 用户信息
     */
    private User user;
    /**
     * 角色列表
     */
    private List<String> roles;
    /**
     * 权限列表
     */
    private Set<String> permissions;

}
