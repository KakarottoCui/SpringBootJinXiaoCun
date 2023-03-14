package com.it.utils;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 创建节点层级关系
 
 */
public class TreeNodeBuilder {
    /**
     * 创建节点层级关系
     *
     * @param treeNodes 菜单集合
     * @param topPid    父节点编号
     * @return
     */
    public static List<TreeNode> build(List<TreeNode> treeNodes, int topPid) {
        List<TreeNode> nodes = new ArrayList<>();
        //循环遍历节点集合
        for (TreeNode t1 : treeNodes) {
            //如果当前节点为根节点，则将当前节点添加到节点数组中
            if (t1.getPid()==topPid) {
                nodes.add(t1);
            }
            //如果当前子节点对应的节点相等，则添加到子节点集合中
            for (TreeNode t2 : treeNodes) {
                if (t1.getId().equals(t2.getPid())) {
                    t1.getChildren().add(t2);
                }
            }
        }
        return nodes;
    }

}