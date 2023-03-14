package com.it.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.aspect.SysLog;
import com.it.entity.*;
import com.it.service.*;
import com.it.utils.DataGridViewResult;
import com.it.utils.DateUtils;
import com.it.vo.InportVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.unit.DataUnit;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private OutsaleService outsaleService;

    @Resource
    private InportService inportService;

    @Resource
    private OutportService outportService;

    @Autowired
    private GoodsService goodsService;


    @SysLog("查询统计销售报表")
    @RequestMapping("/statisticsSales")
    public DataGridViewResult statisticsSales(){
        QueryWrapper<Sale> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select("gid","sum(buyquantity) total,sum(realnumber) actualtotal")
                .groupBy("gid")
                .orderByAsc("total")
                .last("limit 5");
        List<Sale> salesList = saleService.list(queryWrapper);
        Map<String, Object> map = new HashMap<>();
        List<String> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        List<Integer> list3 = new ArrayList<>();

        for (Sale sale : salesList) {
            Goods goods = goodsService.getById(sale.getGid());
            if (null != goods) {
                list1.add(goods.getGname()+goods.getGnumbering());
                list2.add(sale.getTotal());
                list3.add(sale.getActualtotal());
            }
        }
        map.put("data1",list1);
        map.put("data2",list2);
        map.put("data3",list3);
        return new DataGridViewResult(map);


    }

    @SysLog("查询统计退货报表")
    @RequestMapping("/statisticsinGoods")
    public DataGridViewResult statisticsinGoods(){
        QueryWrapper<Inport> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select("sum( number ) as countnumbers,DATE_FORMAT( inptime, '%Y-%m' ) AS counttime")
                .between("inptime",DateUtils.stepMonth(5),new Date())
                .groupBy("counttime")
                .orderByAsc("counttime");
        List<Inport> inportsList = inportService.list(queryWrapper);

        Map<String, Object> map = new HashMap<>();
        List<String> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        for (Inport inport : inportsList) {
                list1.add(inport.getCounttime());
                list2.add(inport.getCountnumbers());
        }
        map.put("data1",list1);
        map.put("data2",list2);
        return new DataGridViewResult(map);
    }

    @SysLog("查询统计退货报表")
    @RequestMapping("/statisticsoutGoods")
    public DataGridViewResult statisticsoutGoods(){
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Outport> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1
                .select("sum( number ) as countnumbers,DATE_FORMAT( outputtime, '%Y-%m' ) AS counttime")
                .between("outputtime",DateUtils.stepMonth(5),new Date())
                .groupBy("counttime")
                .orderByAsc("counttime");
        List<Outport> outportsList = outportService.list(queryWrapper1);

        List<String> list3 = new ArrayList<>();
        List<Integer> list4 = new ArrayList<>();

        for (Outport outport : outportsList) {
            list3.add(outport.getCounttime());
            list4.add(outport.getCountnumbers());
        }
        map.put("data3",list3);
        map.put("data4",list4);
        return new DataGridViewResult(map);
    }

    @SysLog("查询盈利报表")
    @RequestMapping("/profitStatement")
    public DataGridViewResult profitStatement(){
        Map<String, Object> map = new HashMap<>();
        //时间
        List<String> list1 = new ArrayList<>();
        //销售利润
        List<Integer> list2 = new ArrayList<>();
        //实际利润
        List<Integer> list3 = new ArrayList<>();
        //退货
        List<Integer> list4 = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            QueryWrapper<Sale> queryWrapper = new QueryWrapper<>();
            QueryWrapper<Outsale> queryWrapper2 = new QueryWrapper<>();
            queryWrapper
                    .select("sum( money ) AS moneys,DATE_FORMAT( buytime, '%Y-%m' ) AS counttime")
                    .between("buytime",DateUtils.stepMonth(i),DateUtils.getMonth(i))
                    .groupBy("counttime")
                    .orderByDesc("counttime");

            queryWrapper2
                    .select("sum( outprice ) AS moneys,DATE_FORMAT( outtime, '%Y-%m' ) AS counttime")
                    .between("outtime",DateUtils.stepMonth(i),DateUtils.getMonth(i))
                    .groupBy("counttime")
                    .orderByDesc("counttime");

            Sale sales = saleService.getOne(queryWrapper);

            Outsale outsale = outsaleService.getOne(queryWrapper2);

            if(null!= sales){
                list1.add(sales.getCounttime());
                list2.add(sales.getMoneys());
                if(outsale!=null){
                    list3.add(sales.getMoneys()-outsale.getMoneys());
                    list4.add(-outsale.getMoneys());
                }else{
                    list3.add(sales.getMoneys());
                    list4.add(null);
                }
            }else {
                list1.add(new SimpleDateFormat("yyyy-MM").format(DateUtils.getMonth(i)));
                list2.add(null);
                if(outsale!=null){
                    list3.add(-outsale.getMoneys());
                    list4.add(-outsale.getMoneys());
                }else{
                    list3.add(null);
                    list4.add(null);
                }
            }
        }

        map.put("data1",list1);
        map.put("data2",list2);
        map.put("data3",list3);
        map.put("data4",list4);
        return new DataGridViewResult(map);
    }


}
