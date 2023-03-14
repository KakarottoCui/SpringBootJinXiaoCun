package com.it.mapper;

import com.it.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;
import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 
 * @since 2023-03-05
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 删除角色用户关系表数据
     * @param id
     */
    @Delete("delete from tb_user_role where role_id = #{id}")
    void deleRoleUserByRoleId(Serializable id);

    /**
     * 删除角色权限关系表数据
     * @param id
     */
    @Delete("delete from tb_role_permission where role_id = #{id}")
    void deleteRolePermissionByRoleId(Serializable id);

    @Insert("insert into tb_role_permission(role_id,permission_id) values(#{role_id},#{permission_id})")
    void insertRolePermission(@Param("role_id") int roleid, @Param("permission_id") String s);

    /**
     * 根据角色ID查询每个角色下拥有的权限菜单
     * @param roleId
     * @return
     */
    @Select("select permission_id from tb_role_permission where role_id=#{roleId}")
    Set<Integer> findRolePermissionByRoleId(Integer roleId)throws Exception;
}
