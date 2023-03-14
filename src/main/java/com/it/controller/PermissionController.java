package com.it.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.aspect.SysLog;
import com.it.vo.PermissionVO;
import com.it.entity.Permission;
import com.it.service.PermissionService;
import com.it.utils.DataGridViewResult;
import com.it.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 
 * @since 2023-03-03
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /**
     * 菜单列表
     * @param permissionVO
     * @return
     */
    @RequestMapping("/permissionList")
    public DataGridViewResult permissionList(PermissionVO permissionVO){
        //创建分页对象
        IPage<Permission> page = new Page<Permission>(permissionVO.getPage(), permissionVO.getLimit());
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>();
        queryWrapper.eq("type","permission");
        queryWrapper.like(StringUtils.isNotBlank(permissionVO.getTitle()),"title", permissionVO.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(permissionVO.getPercode()),"percode", permissionVO.getPercode());
        queryWrapper.eq(permissionVO.getId()!=null,"id", permissionVO.getId())
                .or().eq(permissionVO.getId()!=null,"pid", permissionVO.getId());
        queryWrapper.orderByAsc("id");
        permissionService.page(page,queryWrapper);
        return new DataGridViewResult(page.getTotal(),page.getRecords());
    }

    /**
     * 添加权限
     * @param permission
     * @return
     */
    @SysLog("权限添加操作")
    @PostMapping("/addPermission")
    public Result addPermission(Permission permission){
        try {
            //设置添加类型
            permission.setType("permission");
            //调用新增的方法
            if(permissionService.save(permission)){
                //新增成功
                return Result.success(true,null,"添加成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success(false,null,"添加失败");
    }

    /**
     * 修改权限
     * @param permission
     * @return
     */
    @SysLog("权限修改操作")
    @PostMapping("/updatePermission")
    public Result updatePermission(Permission permission){
        try {
            if(permissionService.updateById(permission)){
                return Result.success(true,null,"修改成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success(false,null,"修改失败");
    }

    /**
     * 删除权限
     * @param id
     * @return
     */
    @SysLog("权限删除操作")
    @RequestMapping("/deleteById")
    public Result deleteById(int id){
        //删除成功
        if(permissionService.removeById(id)){
            return Result.success(true,null,"删除成功");
        }
        //删除失败
        return Result.success(false,null,"删除失败");
    }

}
