package com.it.service;

import com.it.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 
 * @since 2021-03-30
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     * @throws Exception
     */
    User findUserByUserName(String username) throws Exception;

    Set<Integer> findRoleByUserId(int id)throws Exception;

    boolean saveUserRole(int userid, String roleids)throws Exception;
}
