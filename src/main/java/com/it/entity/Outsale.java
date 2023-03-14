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
 
 * @since 2023-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_outsale")
public class Outsale implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     *
     */
    private Integer sid;
    /**
     * 退货时间
     */
    private Date outtime;

    /**
     * 退货价格
     */
    private Double outprice;

    /**
     * 退货数量
     */
    private Integer number;

    /**
     * 备注
     */
    private String remark;

    private String operateperson;

    private Integer goodsid;

    /**
     * 退货编号
     */
    private String outserial;

    @TableField(exist = false)
    private String salenumber;

    @TableField(exist = false)
    private String goodsname;

    @TableField(exist = false)
    private String gnumbering;

    @TableField(exist = false)
    private Integer moneys;

    @TableField(exist = false)
    private String counttime;




}
