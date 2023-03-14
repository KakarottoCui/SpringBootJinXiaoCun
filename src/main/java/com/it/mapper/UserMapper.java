package com.it.mapper;

import com.it.entity.User;
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
 
 * @since 2021-03-30
 */
public interface UserMapper extends BaseMapper<User> {

    @Delete("delete from tb_user_role where user_id = #{id}")
    void deleRoleUserByUserId(Serializable id);

    @Select("select role_id from tb_user_role where user_id=#{id}")
    Set<Integer> findRoleByUserId(int id);

    @Delete("delete from tb_user_role where user_id = #{userid}")
    void deleteUserRoleByUserId(int userid);

    @Insert("insert into tb_user_role(user_id,role_id) values(#{user_id},#{role_id})")
    void insertUserRole(@Param("user_id")int userid,@Param("role_id") String s);
}
