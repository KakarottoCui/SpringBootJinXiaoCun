package com.it.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 
 * @since 2023-03-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_provider")
public class Provider implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 供应商编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 供应商名称
     */
    private String providername;

    /**
     * 供应商地址
     */
    private String address;

    /**
     * 供应商公司联系电话
     */
    private String telephone;

    /**
     * 添加人
     */
    private String opername;

}
