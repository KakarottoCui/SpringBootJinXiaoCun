package com.it.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.aspect.SysLog;
import com.it.entity.Goods;
import com.it.entity.User;
import com.it.service.GoodsService;
import com.it.utils.Result;
import com.it.vo.InportVO;
import com.it.entity.Inport;
import com.it.entity.Provider;
import com.it.service.InportService;
import com.it.service.ProviderService;
import com.it.utils.DataGridViewResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 
 * @since 2023-03-07
 */
@RestController
@RequestMapping("/inport")
public class InportController {


    @Autowired
    private InportService inportService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private ProviderService providerService;


    /**
     * 进货查询
     *
     * @param
     * @return
     */
    @SysLog("进货查询操作")
    @RequestMapping("/inportList")
    public DataGridViewResult inportList(InportVO inportVO) {

        //创建分页信息    参数1 当前页  参数2 每页显示条数
        IPage<Inport> page = new Page<>(inportVO.getPage(), inportVO.getLimit());
        QueryWrapper<Inport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(inportVO.getProviderid() != null && inportVO.getProviderid() != 0, "providerid", inportVO.getProviderid());
        queryWrapper.eq(inportVO.getGoodsid() != null && inportVO.getGoodsid() != 0, "goodsid", inportVO.getGoodsid());
        queryWrapper.ge(inportVO.getStartTime() != null, "inptime", inportVO.getStartTime());
        queryWrapper.le(inportVO.getEndTime() != null, "inptime", inportVO.getEndTime());
        queryWrapper.orderByDesc("inptime");
        IPage<Inport> inportIPage = inportService.page(page, queryWrapper);
        List<Inport> records = inportIPage.getRecords();
        for (Inport inport : records) {
            inport.setAllinpprice(inport.getInpprice()*inport.getNumber());
            Provider provider = providerService.getById(inport.getProviderid());
            if (null != provider) {
                inport.setProvidername(provider.getProvidername());
            }
            Goods goods = goodsService.getById(inport.getGoodsid());
            if (null != goods) {
                inport.setGoodsname(goods.getGname());
            }
        }
        return new DataGridViewResult(inportIPage.getTotal(), records);

    }


    /**
     * 添加进货信息
     *
     * @param inport
     * @return
     */
    @SysLog("进货添加操作")
    @PostMapping("/addinport")
    public Result addInport(Inport inport, HttpSession session) {

        if (inport.getGoodsid()==0||inport.getProviderid()==0){
            return Result.error(false, null, "添加失败！未选供应商或商品");
        }
        User user = (User) session.getAttribute("username");
        inport.setOperateperson(user.getUname());
        inport.setInptime(new Date());
        boolean bool = inportService.save(inport);
        if (bool) {
            return Result.success(true, "200", "添加成功！");
        }
        return Result.error(false, null, "添加失败！");
    }

    /**
     * 删除单条数据
     *
     * @param id
     * @return
     */
    @SysLog("进货删除操作")
    @RequestMapping("/deleteOne")
    public Result deleteOne(int id) {

        boolean bool = inportService.removeById(id);
        if (bool) {
            return Result.success(true, "200", "删除成功！");
        }
        return Result.error(false, null, "删除失败！");
    }


    /**
     * 修改进货信息
     *
     * @param inport
     * @return
     */
    @SysLog("进货修改操作")
    @PostMapping("/updateinport")
    public Result updateGoods(Inport inport, HttpSession session) {

        User user = (User) session.getAttribute("username");
        inport.setOperateperson(user.getUname());
        inport.setInptime(new Date());
        boolean bool = inportService.updateById(inport);
        if (bool) {
            return Result.success(true, "200", "修改成功！");
        }
        return Result.error(false, null, "修改失败！");
    }

}
