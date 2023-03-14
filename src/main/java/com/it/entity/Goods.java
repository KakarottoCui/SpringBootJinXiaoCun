package com.it.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
 
 * @since 2023-03-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_goods")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    @TableId(value = "gid", type = IdType.AUTO)
    private Integer gid;


    /**
     * 商品名
     */
    private String gname;
    /**
     * 规格
     */
    private String size;
    /**
     * 规格类型
     */
    private String goodspackage;


    /**
     * 商品价格
     */
    private Double gprice;

    /**
     * 商品数量
     */
    private Integer gquantity;
    /**
     * 预警数量
     */
    private Integer dangerquantity;

    /**
     * 商品编号
     */
    private String gnumbering;

    /**
     * 供货商编号
     */
    private Integer providerid;

    @TableField(exist = false)
    private String providername;


}
