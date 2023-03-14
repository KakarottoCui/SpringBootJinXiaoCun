package com.it.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.entity.User;
import com.it.mapper.UserMapper;
import com.it.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 
 * @since 2021-03-30
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     * @throws Exception
     */
    @Override
    public User findUserByUserName(String username) throws Exception {

        //创建条件构造器
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        //根据登录名称查询 key为数据表中列名
        queryWrapper.eq("username",username);
        return userMapper.selectOne(queryWrapper);
    }

    /**
     * 根据用户id查询用户拥有的角色列表
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Set<Integer> findRoleByUserId(int id) throws Exception {
        return userMapper.findRoleByUserId(id);
    }

    @Override
    public boolean saveUserRole(int userid, String roleids) {

        try {
            //删除原有数据
            userMapper.deleteUserRoleByUserId(userid);
            //保存新数据
            String [] idStr = roleids.split(",");
            for (int i = 0; i < idStr.length; i++) {
                userMapper.insertUserRole(userid,idStr[i]);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 重写removeById方法
     * @param id
     * @return
     */
    @Override
    public boolean removeById(Serializable id) {
        //根据角色id删除用户角色关系表的数据
        userMapper.deleRoleUserByUserId(id);
        return super.removeById(id);
    }
}
