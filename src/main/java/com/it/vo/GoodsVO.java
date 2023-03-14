package com.it.vo;


import com.it.entity.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsVO extends Goods {
    /**
     * 当前页码
     */
    private Integer page;

    /**
     * 每页显示数量
     */
    private Integer limit;
}
