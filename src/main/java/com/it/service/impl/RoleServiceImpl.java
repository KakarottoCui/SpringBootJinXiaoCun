package com.it.service.impl;

import com.it.entity.Role;
import com.it.mapper.RoleMapper;
import com.it.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 
 * @since 2023-03-05
 */
@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper rolemapper;

    /**
     * 重写removeById方法
     * @param id
     * @return
     */
    @Override
    public boolean removeById(Serializable id) {
        //根据角色id删除用户角色关系表的数据
        rolemapper.deleRoleUserByRoleId(id);
        //根据角色id删除权限角色关系表的数据
        rolemapper.deleteRolePermissionByRoleId(id);
        return super.removeById(id);
    }

    /**
     * 保存分配权限关系方法
     * @param roleid
     * @param ids
     * @return
     */
    @Override
    public boolean saveRolePermission(int roleid, String ids) throws Exception {
        try {
            //删除原有数据
            rolemapper.deleteRolePermissionByRoleId(roleid);
            //保存新数据
            String [] idStr = ids.split(",");
            if(idStr.length==1){
                return true;
            }
            for (int i = 0; i < idStr.length; i++) {
                rolemapper.insertRolePermission(roleid,idStr[i]);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据角色ID查询每个角色下拥有的权限菜单
     * @param roleId
     * @return
     */
    @Override
    public Set<Integer> findRolePermissionByRoleId(Integer roleId) throws Exception{


        return rolemapper.findRolePermissionByRoleId(roleId);
    }
}
