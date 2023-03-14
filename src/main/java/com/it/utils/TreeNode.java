package com.it.utils;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 树封装
 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeNode {
    /**
     * 菜单节点编号
     */
    private Integer id;
    /**
     * 父节点菜单编号
     */
    @JsonProperty(value = "parentId")
    private Integer pid;
    /**
     * 菜单节点名称
     */
    private String title;
    /**
     * 是否选中
     */
    private String checkArr;

    /**
     * 菜单路径
     */
    private String href;
    /**
     * 是否展开
     */
    private Boolean spread;

    /**
     * 子菜单列表
     */
    private List<TreeNode> children = new ArrayList<>();


    /**
     * 构建树节点菜单
     * @param id    节点编号
     * @param pid   父节点
     * @param title 节点标题
     * @param href  节点菜单路径
     * @param spread    节点展开状态
     */
    public TreeNode(Integer id, Integer pid, String title, String href,Boolean spread) {
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.href = href;
        this.spread = spread;
    }
    /**
     * 构建树节点菜单
     * @param id    节点编号
     * @param pid   父节点
     * @param title 节点标题
     */
    public TreeNode(Integer id, Integer pid, String title,Boolean spread) {
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.spread = spread;
    }

    public TreeNode(Integer id, Integer pid, String title,Boolean spread,String checkArr) {
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.spread = spread;
        this.checkArr =checkArr;
    }

}