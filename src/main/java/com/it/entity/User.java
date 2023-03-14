package com.it.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

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
 
 * @since 2021-03-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "uid", type = IdType.AUTO)
    private Integer uid;

    /**
     * 账户
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     *盐
     */
    private String salt;


    /**
     * 姓名
     */
    private String uname;

    /**
     * 性别
     */
    private Integer usex;

    /**
     * 手机
     */
    private String uphone;

    /**
     * 邮箱
     */
    private String uemail;

    /**
     * 状态 0 启用 1 停用
     */
    private Integer available;

    /**
     * 创建时间
     */
    private Date ucreatetime;

    /**
     * 用户类型
     */
    private Integer type;



}
