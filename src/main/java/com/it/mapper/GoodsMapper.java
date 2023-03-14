package com.it.mapper;

import com.it.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.io.Serializable;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 
 * @since 2023-03-07
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    @Delete("delete from tb_goods_category where goodsid = #{goodsid}")
    void deleteUserRoleByUserId(Serializable goodsid);

    @Insert("insert into tb_goods_category(goodsid,categoryid) values(#{goodsid},#{categoryid})")
    void insertGoodsCategory(@Param("goodsid") int goodsid,@Param("categoryid") String s);


}
