package com.it.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.aspect.SysLog;
import com.it.vo.UserVO;
import com.it.entity.User;
import com.it.service.RoleService;
import com.it.service.UserService;
import com.it.utils.DataGridViewResult;
import com.it.utils.PasswordUtil;
import com.it.utils.Result;
import com.it.vo.LoginUserVO;
import com.it.utils.UUIDUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 
 * @since 2021-03-30
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @param request
     * @return
     */

    @SysLog("登陆操作")
    @PostMapping("/login")
    public Result login(String username, String password, HttpServletRequest request) {
        try {
            //获取当前登录主体对象
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
            LoginUserVO userDTO = (LoginUserVO) subject.getPrincipal();
            request.getSession().setAttribute("username", userDTO.getUser());
            return Result.success(true, "200", "登录成功");
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            return Result.error(false, "400", "登录失败,用户名不存在");
        }catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            return Result.error(false, "400", "登录失败,密码错误");
        }catch (AuthenticationException e) {
            e.printStackTrace();
            return Result.error(false, "400", "登录失败,账户禁用");
        }
    }


    /**
     * 查询所有用户信息
     *
     * @param userVO
     * @return
     */
    @SysLog("用户查询操作")
    @RequestMapping("/userList")
    public DataGridViewResult userList(UserVO userVO) {
        //分页构造函数
        IPage<User> page = new Page<>(userVO.getPage(), userVO.getLimit());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(userVO.getUsername()), "username", userVO.getUsername());
        queryWrapper.like(!StringUtils.isEmpty(userVO.getUname()), "uname", userVO.getUname());
        /**
         * 翻页查询
         * @param page         翻页对象
         * @param queryWrapper 实体对象封装操作类
         */
        IPage<User> userIPage = userService.page(page, queryWrapper);
        return new DataGridViewResult(userIPage.getTotal(), userIPage.getRecords());
    }

    /**
     * 添加用户信息
     *
     * @param user
     * @return
     */
    @SysLog("用户添加操作")
    @PostMapping("/adduser")
    public Result addRole(User user) {


        user.setUcreatetime(new Date());
        String salt = UUIDUtil.randomUUID();
        user.setPassword(PasswordUtil.md5("000000", salt, 2));
        user.setSalt(salt);
        user.setType(1);
        boolean bool = userService.save(user);

        try {
            if (bool) {
                return Result.success(true, "200", "添加成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error(false, null, "添加失败！");
    }

    /**
     * 校验用户名是否存在
     *
     * @param username
     * @return
     */

    @RequestMapping("/checkUserName")
    public String checkUserName(String username) {
        Map<String, Object> map = new HashMap<>();
        try {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            User user = userService.getOne(queryWrapper);
            if (user != null) {
                map.put("exist", true);
                map.put("message", "用户名已存在");
            } else {
                map.put("exist", false);
                map.put("message", "用户名可以使用");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(map);
    }

    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    @SysLog("用户修改操作")
    @PostMapping("/updateuser")
    public Result updateUser(User user) {

        boolean bool = userService.updateById(user);
        try {
            if (bool) {
                return Result.success(true, "200", "修改成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error(false, null, "修改失败！");
    }

    /**
     * 删除单条数据
     *
     * @param id
     * @return
     */
    @SysLog("用户删除操作")
    @RequestMapping("/deleteOne")
    public Result deleteOne(int id) {
        boolean bool = userService.removeById(id);
        try {
            if (bool) {
                return Result.success(true, "200", "删除成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error(false, null, "删除失败！");
    }

    /**
     * 重置密码
     *
     * @param id
     * @return
     */
    @SysLog("用户修改操作")
    @PostMapping("/resetPwd")
    public Result resetPwd(int id) {

        User user = new User();
        String salt = UUIDUtil.randomUUID();
        user.setUid(id);
        user.setPassword(PasswordUtil.md5("000000", salt, 2));
        user.setSalt(salt);
        boolean bool = userService.updateById(user);

        try {
            if (bool) {
                return Result.success(true, "200", "重置成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error(false, null, "重置失败！");
    }

    /**
     * 根据id查询当前用户拥有的角色
     *
     * @param id
     * @return
     */
    @RequestMapping("/initRoleByUserId")
    public DataGridViewResult initRoleByUserId(int id) {
        List<Map<String, Object>> mapList = null;
        try {
            //查询所有角色列表
            mapList = roleService.listMaps();
            //根据用户id查询用户拥有的角色
            Set<Integer> roleIdList = userService.findRoleByUserId(id);
            for (Map<String, Object> map : mapList) {
                //定义标记 默认不选中
                boolean flag = false;
                int roleId = (int) map.get("roleid");
                for (Integer rid : roleIdList) {
                    if (rid == roleId) {
                        flag = true;
                        break;
                    }
                }
                map.put("LAY_CHECKED", flag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DataGridViewResult(Long.valueOf(mapList.size()), mapList);

    }

    /**
     * 为用户分配角色
     *
     * @param roleids
     * @param userid
     * @return
     */
    @SysLog("用户添加操作")
    @RequestMapping("/saveUserRole")
    public Result saveUserRole(String roleids, int userid) {

        try {
            if (userService.saveUserRole(userid, roleids)) {
                return Result.success(true, null, "分配成功");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error(false, null, "分配失败");

    }

    /**
     * 修改密码
     *
     * @param newPassWord1
     * @param newPassWord2
     * @return
     */
    @RequestMapping("/updateUserPassWord")
    public Result updateUserPassWord(String newPassWord1, String newPassWord2,HttpSession session) {
        User sessionUser = (User) session.getAttribute("username");

        if (newPassWord1.equals(newPassWord2)){
            User user = new User();
            String salt = UUIDUtil.randomUUID();
            user.setUid(sessionUser.getUid());
            user.setPassword(PasswordUtil.md5(newPassWord1, salt, 2));
            user.setSalt(salt);
            boolean bool = userService.updateById(user);
            if (bool){
                return Result.success(true,null,"修改成功");
            }else {
                return Result.error(false,null,"修改失败!");
            }
        }else {
            return Result.error(false,null,"修改失败，两次密码不一致!");
        }

    }

}

