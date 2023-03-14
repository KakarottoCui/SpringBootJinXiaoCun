package com.it.mapper;

import com.it.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 
 * @since 2023-03-04
 */
public interface CategoryMapper extends BaseMapper<Category> {

    @Select("select categoryid from tb_goods_category where goodsid=#{id}")
    Set<Integer> findGoodsByCategoryId(int id);
}
