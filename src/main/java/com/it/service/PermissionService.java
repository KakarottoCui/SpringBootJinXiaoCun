package com.it.service;

import com.it.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 
 * @since 2023-03-03
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 根据角色id查询对应权限
     * @param roleId
     * @return
     */
    List<Integer> findRolePermissionIdByRoleId(int roleId);
}
