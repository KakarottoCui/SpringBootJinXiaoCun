package com.it.service.impl;

import com.it.entity.Goods;
import com.it.entity.Inport;
import com.it.entity.Sale;
import com.it.mapper.GoodsMapper;
import com.it.mapper.SaleMapper;
import com.it.service.SaleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 
 * @since 2023-03-10
 */
@Service
public class SaleServiceImpl extends ServiceImpl<SaleMapper, Sale> implements SaleService {

    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public boolean save(Sale entity) {

        Goods goods = goodsMapper.selectById(entity.getGid());

        if (goods.getGquantity() - goods.getDangerquantity() - entity.getBuyquantity() >= 0) {
            goods.setGquantity(goods.getGquantity() - entity.getBuyquantity());
            goodsMapper.updateById(goods);
            return super.save(entity);
        }else {
            return false;
        }

    }

    @Override
    public boolean updateById(Sale entity) {

        Sale sale = baseMapper.selectById(entity.getSaleid());
        Goods goods = goodsMapper.selectById(entity.getGid());
        goods.setGquantity(goods.getGquantity()-sale.getBuyquantity()+entity.getBuyquantity());
        goodsMapper.updateById(goods);
        return super.updateById(entity);
    }
}
