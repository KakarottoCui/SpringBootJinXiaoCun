package com.it.service;

import com.it.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 
 * @since 2023-03-05
 */
public interface RoleService extends IService<Role> {

    /**
     * 保存分配权限关系方法
     * @param roleid
     * @param ids
     * @return
     */
    boolean saveRolePermission(int roleid, String ids) throws Exception;

    /**
     * 根据角色ID查询每个角色下拥有的权限菜单
     * @param roleId
     * @return
     */
    Set<Integer> findRolePermissionByRoleId(Integer roleId)throws Exception;
}
