package com.it.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 
 * @since 2023-03-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_inport")
public class Inport implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Date inptime;

    private String operateperson;

    private Integer number;

    private String remark;

    private Double inpprice;

    private Integer providerid;

    private Integer goodsid;

    @TableField(exist = false)
    private String providername;

    @TableField(exist = false)
    private String goodsname;

    @TableField(exist = false)
    private Double allinpprice;


    @TableField(exist = false)
    private Integer countnumbers;

    @TableField(exist = false)
    private String counttime;


}
