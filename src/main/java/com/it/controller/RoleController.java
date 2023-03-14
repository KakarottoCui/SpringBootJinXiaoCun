package com.it.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.aspect.SysLog;
import com.it.vo.RoleVO;
import com.it.entity.*;
import com.it.entity.Role;
import com.it.service.PermissionService;
import com.it.service.RoleService;
import com.it.utils.DataGridViewResult;
import com.it.utils.Result;
import com.it.utils.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 
 * @since 2023-03-05
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 查询所有角色信息
     * @param roleVO
     * @return
     */
    @SysLog("角色查询操作")
    @RequestMapping("/roleList")
    public DataGridViewResult roleList(RoleVO roleVO) {
        //分页构造函数
        IPage<Role> page = new Page<>(roleVO.getPage(), roleVO.getLimit());
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(roleVO.getRolename()), "rolename", roleVO.getRolename());
        /**
         * 翻页查询
         *
         * @param page         翻页对象
         * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
         */
        IPage<Role> roleIPage = roleService.page(page, queryWrapper);
        return new DataGridViewResult(roleIPage.getTotal(), roleIPage.getRecords());
    }

    /**
     * 添加角色信息
     *
     * @param role
     * @return
     */
    @SysLog("角色添加操作")
    @PostMapping("/addrole")
    public Result addRole(Role role) {
        boolean bool = roleService.save(role);
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
     * 修改角色信息
     *
     * @param role
     * @return
     */
    @SysLog("角色修改操作")
    @PostMapping("/updaterole")
    public Result updateRole(Role role) {
        boolean bool = roleService.updateById(role);
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
    @SysLog("角色删除操作")
    @RequestMapping("/deleteOne")
    public Result deleteOne(int id) {
        boolean bool = roleService.removeById(id);
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
     * 初始化权限菜单树
     *
     * @param roleId
     * @return
     */
    @RequestMapping("/initPermissionByRoleId")
    public DataGridViewResult initPermissionByRoleId(int roleId) {
        //创建条件构造器对象
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        List<Permission> permissionList = permissionService.list();
        List<Integer> currentPermissionIds = permissionService.findRolePermissionIdByRoleId(roleId);
        //保存角色拥有的菜单
        List<Permission> currentPermissions = new ArrayList<>();
        if (currentPermissionIds != null && currentPermissionIds.size() > 0) {
            queryWrapper.in("id", currentPermissionIds);
            currentPermissions = permissionService.list(queryWrapper);
        }
        List<TreeNode> treeNodes = new ArrayList<>();
        for (Permission p1 : permissionList) {
            //定义变量标记是否选中
            String checkArr = "0";
            for (Permission p2 : currentPermissions) {
                if (p1.getId().equals(p2.getId())) {
                    checkArr = "1";
                    break;
                }
            }
            Boolean spread = p1.getSpread() == 1 ? true : false;
            treeNodes.add(new TreeNode(p1.getId(), p1.getPid(), p1.getTitle(), spread, checkArr));
        }
        return new DataGridViewResult(treeNodes);
    }

    /**
     * 保存分配权限关系方法
     *
     * @param roleid
     * @param ids
     * @return
     */
    @SysLog("角色添加操作")
    @RequestMapping("/saveRolePermission")
    public Result saveRolePermission(int roleid, String ids) {

        try {
            if (roleService.saveRolePermission(roleid, ids)) {
                return Result.success(true, null, "分配成功");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error(false, null, "分配失败");

    }
}
