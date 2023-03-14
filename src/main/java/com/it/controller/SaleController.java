package com.it.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.aspect.SysLog;
import com.it.entity.*;
import com.it.service.CustomerService;
import com.it.service.GoodsService;
import com.it.service.SaleService;
import com.it.utils.DataGridViewResult;
import com.it.utils.Result;
import com.it.vo.SaleVO;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 
 * @since 2023-03-10
 */
@RestController
@RequestMapping("/sale")
public class SaleController {
    @Autowired
    private SaleService saleService;

    @Autowired
    private GoodsService goodsService;
    
    @Autowired
    private CustomerService customerService;

    /**
     * 销售查询
     *
     * @param
     * @return
     */
    @SysLog("销售查询操作")
    @RequestMapping("/saleList")
    public DataGridViewResult saleList(SaleVO saleVO) {

        //创建分页信息    参数1 当前页  参数2 每页显示条数
        IPage<Sale> page = new Page<>(saleVO.getPage(), saleVO.getLimit());
        QueryWrapper<Sale> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(saleVO.getNumbering()),"numbering", saleVO.getNumbering());
        queryWrapper.eq(saleVO.getGid() != null && saleVO.getGid() != 0, "gid", saleVO.getGid());
        queryWrapper.ge(saleVO.getStartTime() != null, "buytime", saleVO.getStartTime());
        queryWrapper.le(saleVO.getEndTime() != null, "buytime", saleVO.getEndTime());

        queryWrapper.orderByDesc("buytime");

        IPage<Sale> saleIPage = saleService.page(page, queryWrapper);

        List<Sale> records = saleIPage.getRecords();

        for (Sale sale : records) {
            sale.setAllmoney(sale.getMoney()*sale.getBuyquantity());
            Customer customer = customerService.getById(sale.getCustid());

            if (null != customer) {
                sale.setCustomervip(customer.getCustvip());
                sale.setCustomername(customer.getCustname());
            }
            Goods goods = goodsService.getById(sale.getGid());
            if (null != goods) {

                sale.setGoodsname(goods.getGname());
                sale.setGnumbering(goods.getGnumbering());
            }
        }

        return new DataGridViewResult(saleIPage.getTotal(), records);

    }


    /**
     * 添加销售单信息
     *
     * @param sale
     * @return
     */
    @SysLog("销售添加操作")
    @PostMapping("/addsale")
    public Result addsale(Sale sale, HttpSession session) {
        if (sale.getGid()==0){
            return Result.error(false, null, "添加失败！未选商品");
        }
        Goods goods = goodsService.getById(sale.getGid());
        Integer gquantity = goods.getGquantity();
        if(gquantity<sale.getBuyquantity()){
            return Result.error(false, null, "添加失败！库存不足,库存为："+gquantity);
        }
        User user = (User) session.getAttribute("username");
        String num = RandomStringUtils.randomAlphanumeric(7);
        sale.setNumbering(num);
        sale.setPerson(user.getUsername());
        sale.setBuytime(new Date());
        sale.setRealnumber(sale.getBuyquantity());
        boolean bool = saleService.save(sale);
        if (bool) {
            return Result.success(true, "200", "添加成功！");
        }
        return Result.error(false, null, "添加失败！库存不足");
    }


    /**
     * 修改销售单信息
     *
     * @param sale
     * @return
     */
    @SysLog("销售修改操作")
    @PostMapping("/updatesale")
    public Result updatesale(Sale sale, HttpSession session) {

        User user = (User) session.getAttribute("username");
        sale.setPerson(user.getUsername());
        sale.setBuytime(new Date());
        boolean bool = saleService.updateById(sale);
        if (bool) {
            return Result.success(true, "200", "修改成功！");
        }
        return Result.error(false, null, "修改失败！");
    }
    /**
     * 删除单条数据
     *
     * @param id
     * @return
     */
    @SysLog("销售删除操作")
    @RequestMapping("/deleteOne")
    public Result deleteOne(int id) {

        boolean bool = saleService.removeById(id);
        if (bool) {
            return Result.success(true, "200", "删除成功！");
        }
        return Result.error(false, null, "删除失败！");
    }



}
