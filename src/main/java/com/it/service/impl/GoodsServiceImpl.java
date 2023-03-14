package com.it.service.impl;

import com.it.entity.Goods;
import com.it.mapper.GoodsMapper;
import com.it.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 
 * @since 2023-03-07
 */
@Service
@Transactional
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 添加商品类别
     * @param goodsid
     * @param categoryids
     * @return
     */
    @Override
    public boolean saveGoodsCategory(int goodsid, String categoryids) {
        try {
            //删除原有数据
            goodsMapper.deleteUserRoleByUserId(goodsid);
            //保存新数据
            String [] idStr = categoryids.split(",");
            for (int i = 0; i < idStr.length; i++) {
                goodsMapper.insertGoodsCategory(goodsid,idStr[i]);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除商品
     * @param id
     * @return
     */
    @Override
    public boolean removeById(Serializable id) {
        goodsMapper.deleteUserRoleByUserId(id);
        return super.removeById(id);
    }
}
