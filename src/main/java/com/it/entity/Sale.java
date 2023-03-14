package com.it.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 
 * @since 2023-03-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_sale")
public class Sale implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "saleid", type = IdType.AUTO)
    private Integer saleid;

    /**
     * 商品外键
     */
    private Integer gid;

    /**
     * 客户外键
     */
    private Integer custid;

    /**
     * 销售数量
     */
    private Integer buyquantity;



    /**
     * 销售时间
     */
    private Date buytime;

    /**
     * 销售金额
     */
    private Double money;

    /**
     * 备注
     */
    private String remark;

    /**
     * 操作人
     */
    private String person;

    /**
     * 销售编号
     */
    private String numbering;

    private Integer realnumber;

    @TableField(exist = false)
    private String customervip;

    @TableField(exist = false)
    private String customername;

    @TableField(exist = false)
    private String goodsname;

    @TableField(exist = false)
    private Double allmoney;

    @TableField(exist = false)
    private String gnumbering;

    /**
     * 实际销量
     */
    @TableField(exist = false)
    private Integer actualtotal;

    /**
     * 销量
     */
    @TableField(exist = false)
    private Integer total;

    @TableField(exist = false)
    private Integer moneys;

    @TableField(exist = false)
    private String counttime;

}
