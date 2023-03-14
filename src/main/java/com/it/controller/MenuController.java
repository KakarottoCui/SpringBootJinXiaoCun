package com.it.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.entity.Permission;
import com.it.entity.User;
import com.it.service.PermissionService;
import com.it.service.RoleService;
import com.it.service.UserService;
import com.it.utils.DataGridViewResult;
import com.it.utils.TreeNode;
import com.it.utils.TreeNodeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 
 * @since 2023-03-03
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    /**
     * 加载首页左侧菜单
     *
     * @param session
     * @return
     */
    @RequestMapping("/loadIndexLeftMenuTree")
    public DataGridViewResult loadIndexLeftMenuTree(HttpSession session) {
        //调用查询权限菜单列表
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", "menu");
        User username = (User) session.getAttribute("username");
        List<Permission> permissionList = new ArrayList<>();

        if (username.getType() == 0) {
            permissionList = permissionService.list(queryWrapper);
        }
        try {
            //1.根据当前登录用户ID查询该用户拥有的角色列表
            Set<Integer> currentUserRoleIds = userService.findRoleByUserId(username.getUid());
            //2.创建集合保存每个角色下拥有的权限菜单ID
            Set<Integer> pids = new HashSet<Integer>();
            //3.循环遍历当前用户拥有的角色列表
            for (Integer roleId : currentUserRoleIds) {
                //4.根据角色ID查询每个角色下拥有的权限菜单
                Set<Integer> permissionIds = roleService.findRolePermissionByRoleId(roleId);
                //5.将查询出来的权限id放到集合中
                pids.addAll(permissionIds);
            }
            //判断当前权限集合是否有数据
            if (pids.size() > 0) {
                //拼接查询条件
                queryWrapper.in("id", pids);
                //执行查询
                permissionList = permissionService.list(queryWrapper);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //创建集合 保存树节点
        List<TreeNode> treeNodes = new ArrayList<>();

        for (Permission permission : permissionList) {
            TreeNode treeNode = new TreeNode();
            //菜单节点
            treeNode.setId(permission.getId());
            //父节点
            treeNode.setPid(permission.getPid());
            //菜单路径
            treeNode.setHref(permission.getHref());
            //菜单名字
            treeNode.setTitle(permission.getTitle());
            //是否展开
            treeNode.setSpread(false);
            treeNodes.add(treeNode);
        }
        //构建节点菜单层级关系(参数1：节点集合数据源，参数2：根节点编号)
        List<TreeNode> treeNodeList = TreeNodeBuilder.build(treeNodes, 1);

        return new DataGridViewResult(treeNodeList);
    }


    /**
     * 加载菜单管理页面左侧菜单树
     *
     * @return
     */
    @RequestMapping("/loadMenuTreeLeft")
    public DataGridViewResult loadMenuTreeLeft() {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        //只查询菜单，不查权限
        queryWrapper.eq("type", "menu");
        //查询所有菜单
        List<Permission> permissionList = permissionService.list(queryWrapper);
        //创建节点集合
        List<TreeNode> treeNodes = new ArrayList<>();
        //循环遍历菜单集合
        for (Permission permission : permissionList) {
            Boolean spread = permission.getSpread() == 1 ? true : false;
            treeNodes.add(new TreeNode(permission.getId(), permission.getPid(), permission.getTitle(), spread, "0"));
        }
        DataGridViewResult dataGridViewResult = new DataGridViewResult(treeNodes);
        return dataGridViewResult;
    }


}
