package com.it.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.aspect.SysLog;
import com.it.entity.*;
import com.it.service.GoodsService;
import com.it.service.InportService;
import com.it.service.OutportService;
import com.it.service.ProviderService;
import com.it.utils.DataGridViewResult;
import com.it.utils.Result;
import com.it.vo.InportVO;
import com.it.vo.OutportVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 
 * @since 2023-03-07
 */
@RestController
@RequestMapping("/outport")
public class OutportController {

    @Autowired
    private OutportService outportService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private InportService inportService;

    @Autowired
    private ProviderService providerService;

    /**
     * 添加退货信息
     *
     * @param id
     * @param number
     * @param remark
     * @return
     */
    @SysLog("退货添加操作")
    @RequestMapping("addOutport")
    public Result addOutport(Integer id, Integer number, String remark, HttpSession session) {
        try {
            outportService.addOutport(id, number, remark,session);
            return Result.success(true, "200", "添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(false, null, "添加失败！");
        }

    }


    /**
     * 退货查询
     *
     * @param
     * @return
     */
    @SysLog("退货查询操作")
    @RequestMapping("/outportList")
    public DataGridViewResult outportList(OutportVO outportVO) {

        //创建分页信息    参数1 当前页  参数2 每页显示条数
        IPage<Outport> page = new Page<>(outportVO.getPage(), outportVO.getLimit());
        QueryWrapper<Outport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(outportVO.getProviderid() != null && outportVO.getProviderid() != 0, "providerid", outportVO.getProviderid());
        queryWrapper.eq(outportVO.getGoodsid() != null && outportVO.getGoodsid() != 0, "goodsid", outportVO.getGoodsid());
        queryWrapper.ge(outportVO.getStartTime() != null, "outputtime", outportVO.getStartTime());
        queryWrapper.le(outportVO.getEndTime() != null, "outputtime", outportVO.getEndTime());

        queryWrapper.orderByDesc("outputtime");

        IPage<Outport> outportIPage = outportService.page(page, queryWrapper);

        List<Outport> records = outportIPage.getRecords();

        for (Outport outport : records) {
            Provider provider = providerService.getById(outport.getProviderid());

            if (null != provider) {
                outport.setProvidername(provider.getProvidername());
            }
            Goods goods = goodsService.getById(outport.getGoodsid());
            if (null != goods) {
                outport.setGoodsname(goods.getGname());
                outport.setGnumbering(goods.getGnumbering());
            }
        }
        return new DataGridViewResult(outportIPage.getTotal(), records);

    }

}
