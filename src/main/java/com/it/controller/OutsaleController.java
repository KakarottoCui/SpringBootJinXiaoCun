package com.it.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.aspect.SysLog;
import com.it.entity.Goods;
import com.it.entity.Outsale;
import com.it.entity.Provider;
import com.it.entity.Sale;
import com.it.service.*;
import com.it.utils.DataGridViewResult;
import com.it.utils.Result;
import com.it.vo.OutSaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 
 * @since 2023-03-26
 */
@RestController
@RequestMapping("/outsale")
public class OutsaleController {

    @Autowired
    private OutsaleService outsaleService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SaleService saleService;

    @Autowired
    private ProviderService providerService;

    /**
     * 退货查询
     *
     * @param
     * @return
     */
    @SysLog("销售退货查询操作")
    @RequestMapping("/outsaleList")
    public DataGridViewResult outsaleList(OutSaleVO outsaleVO) {

        //创建分页信息    参数1 当前页  参数2 每页显示条数
        IPage<Outsale> page = new Page<>(outsaleVO.getPage(), outsaleVO.getLimit());
        QueryWrapper<Outsale> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(outsaleVO.getOutserial()),"outserial", outsaleVO.getOutserial());
        queryWrapper.ge(outsaleVO.getStartTime() != null, "outtime", outsaleVO.getStartTime());
        queryWrapper.le(outsaleVO.getEndTime() != null, "outtime", outsaleVO.getEndTime());
        queryWrapper.orderByDesc("outtime");
        IPage<Outsale> outsaleIPage = outsaleService.page(page, queryWrapper);
        List<Outsale> records = outsaleIPage.getRecords();

        for (Outsale outsale : records) {
            Sale sale = saleService.getById(outsale.getSid());

            if (null != sale) {
                outsale.setSalenumber(sale.getNumbering());
            }

            Goods goods = goodsService.getById(sale.getGid());
            if (null != goods) {
                outsale.setGoodsname(goods.getGname());
                outsale.setGnumbering(goods.getGnumbering());
            }

        }
        return new DataGridViewResult(outsaleIPage.getTotal(), records);

    }

    /**
     * 添加退货信息
     *
     * @param id
     * @param number
     * @param remark
     * @return
     */
    @SysLog("退货添加操作")
    @RequestMapping("addOutsale")
    public Result addOutsale(Integer id, Integer number, String remark,Double money, HttpSession session) {
        try {
            outsaleService.addOutsale(id, number, remark,money,session);
            return Result.success(true, "200", "添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(false, null, "添加失败！");
        }

    }

}
