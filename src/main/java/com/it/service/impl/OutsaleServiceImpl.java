package com.it.service.impl;

import com.it.entity.*;
import com.it.mapper.GoodsMapper;
import com.it.mapper.OutsaleMapper;
import com.it.mapper.SaleMapper;
import com.it.service.OutsaleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 
 * @since 2023-03-26
 */
@Service
public class OutsaleServiceImpl extends ServiceImpl<OutsaleMapper, Outsale> implements OutsaleService {

    @Resource
    private SaleMapper saleMapper;

    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public void addOutsale(Integer id, Integer number, String remark, Double money, HttpSession session) {
        User user = (User) session.getAttribute("username");
        //进货信息查询
        Sale sale = saleMapper.selectById(id);
        sale.setRealnumber(sale.getRealnumber()-number);
        saleMapper.updateById(sale);
        //查询商品信息
        Goods goods = goodsMapper.selectById(sale.getGid());

        //更新库存
        goods.setGquantity(goods.getGquantity() + number);
        goodsMapper.updateById(goods);

        //添加退货单信息
        String rid = RandomStringUtils.randomAlphanumeric(7);
        Outsale outsale = new Outsale();
        outsale.setGoodsid(goods.getGid());
        outsale.setOuttime(new Date());
        outsale.setRemark(remark);
        outsale.setOperateperson(user.getUname());
        outsale.setOutprice(money * number);
        outsale.setNumber(number);
        outsale.setOutserial(rid);
        outsale.setSid(id);
        baseMapper.insert(outsale);
    }
}
