package com.it.service.impl;

import com.it.entity.Goods;
import com.it.entity.Inport;
import com.it.entity.Outport;
import com.it.entity.User;
import com.it.mapper.GoodsMapper;
import com.it.mapper.InportMapper;
import com.it.mapper.OutportMapper;
import com.it.service.OutportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.utils.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 
 * @since 2023-03-07
 */
@Service
@Transactional
public class OutportServiceImpl extends ServiceImpl<OutportMapper, Outport> implements OutportService {


    @Resource
    private InportMapper inportMapper;

    @Resource
    private GoodsMapper goodsMapper;


    /**
     * 自定义退货方法
     * @param id
     * @param number
     * @param remark
     */
    @Override
    public void addOutport(Integer id, Integer number, String remark, HttpSession session) {
        User user = (User) session.getAttribute("username");
        //进货信息查询
        Inport inport = inportMapper.selectById(id);
        //查询商品信息
        Goods goods = goodsMapper.selectById(inport.getGoodsid());
        if (goods.getGquantity()>=number){
            //更新库存
            goods.setGquantity(goods.getGquantity()-number);
            goodsMapper.updateById(goods);
            //添加退货单信息
            Outport outport = new Outport();
            outport.setGoodsid(goods.getGid());
            outport.setOutputtime(new Date());
            outport.setRemark(remark);
            outport.setOperateperson(user.getUname());
            outport.setOutprice(inport.getInpprice()*number);
            outport.setProviderid(inport.getProviderid());
            outport.setNumber(number);
            baseMapper.insert(outport);
        }else {

        }


    }
}
