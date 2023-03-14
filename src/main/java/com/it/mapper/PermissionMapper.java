package com.it.mapper;

import com.it.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 
 * @since 2023-03-03
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     *
     * @param roleId
     * @return
     */
    @Select("select permission_id from tb_role_permission where role_id =#{roleId}")
    List<Integer> findRolePermissionIdByRoleId(int roleId);

    /**
     * 根据权限id删除角色权限表对应的数据
     * @param id
     */
    @Delete("delete from tb_role_permission where permission_id=#{id}")
    void deleteRolePermissionByPermisssionid(Serializable id);
}
