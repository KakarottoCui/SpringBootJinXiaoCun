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
 
 * @since 2023-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("tb_logs")
public class Logs implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "lid", type = IdType.AUTO)
    private Integer lid;

    /**
     * 登陆用户
     */
    private String uname;

    /**
     * 登陆时间
     */
    private Date ltime;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 日志内容
     */
    private String content;

    /**
     * 操作类型
     */
    private String type;

    public Logs(String uname, Date ltime, String ip, String content, String type) {
        this.uname = uname;
        this.ltime = ltime;
        this.ip = ip;
        this.content = content;
        this.type = type;
    }
}
