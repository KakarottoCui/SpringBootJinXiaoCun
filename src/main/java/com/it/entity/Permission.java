package com.it.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 
 * @since 2023-03-03
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_permission")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 一级id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 二级对应一级id
     */
    private Integer pid;

    /**
     * 类型
     */
    private String type;

    /**
     * 名称
     */
    private String title;

    /**
     *  路径
     */
    private String href;

    /**
     * 权限编码
     */
    private String percode;

    /**
     * 是否展开
     */
    private Integer spread;

    /**
     * 是否可用
     */
    private Integer available;


}
