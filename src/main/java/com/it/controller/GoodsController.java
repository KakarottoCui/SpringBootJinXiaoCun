package com.it.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.aspect.SysLog;
import com.it.vo.GoodsVO;
import com.it.entity.Goods;
import com.it.entity.Provider;
import com.it.service.CategoryService;
import com.it.service.GoodsService;
import com.it.service.ProviderService;
import com.it.utils.DataGridViewResult;
import com.it.utils.Result;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 
 * @since 2023-03-07
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {


    @Autowired
    private GoodsService goodsService;

    @Autowired
    private ProviderService providerService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 商品模糊查询
     *
     * @param
     * @return
     */
    @SysLog("商品查询操作")
    @RequestMapping("/goodsList")
    public DataGridViewResult goodsList(GoodsVO goodsVO) {
        //创建分页信息    参数1 当前页  参数2 每页显示条数
        IPage<Goods> page = new Page<>(goodsVO.getPage(), goodsVO.getLimit());
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(goodsVO.getProviderid() != null && goodsVO.getProviderid() != 0, "providerid", goodsVO.getProviderid());
        queryWrapper.like(!StringUtils.isEmpty(goodsVO.getGname()), "gname", goodsVO.getGname());
        IPage<Goods> goodsIPage = goodsService.page(page, queryWrapper);
        List<Goods> records = goodsIPage.getRecords();
        for (Goods goods : records) {
            Provider provider = providerService.getById(goods.getProviderid());
            if (null != provider) {
                goods.setProvidername(provider.getProvidername());
            }
        }
        return new DataGridViewResult(goodsIPage.getTotal(), records);
    }


    /**
     * 添加商品信息
     *
     * @param goods
     * @return
     */
    @SysLog("商品添加操作")
    @PostMapping("/addgoods")
    public Result addGoods(Goods goods) {
        String id = RandomStringUtils.randomAlphanumeric(8);
        goods.setGnumbering(id);
        boolean bool = goodsService.save(goods);
        if (bool) {
            return Result.success(true, "200", "添加成功！");
        }
        return Result.error(false, null, "添加失败！");
    }

    /**
     * 修改商品信息
     *
     * @param goods
     * @return
     */
    @SysLog("商品修改操作")
    @PostMapping("/updategoods")
    public Result updateGoods(Goods goods) {

        boolean bool = goodsService.updateById(goods);
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
    @SysLog("商品删除操作")
    @RequestMapping("/deleteOne")
    public Result deleteOne(int id) {

        boolean bool = goodsService.removeById(id);
        if (bool) {
            return Result.success(true, "200", "删除成功！");
        }
        return Result.error(false, null, "删除失败！");
    }

    /**
     * 根据id查询当前商品拥有的类别
     *
     * @param id
     * @return
     */
    @RequestMapping("/initGoodsByCategoryId")
    public DataGridViewResult initGoodsByCategoryId(int id) {
        List<Map<String, Object>> mapList = null;
        try {
            //查询所有类别列表
            mapList = categoryService.listMaps();
            //根据商品id查询商品拥有的类别
            Set<Integer> cateIdList = categoryService.findGoodsByCategoryId(id);
            for (Map<String, Object> map : mapList) {
                //定义标记 默认不选中
                boolean flag = false;
                int cateId = (int) map.get("cateid");
                for (Integer cid : cateIdList) {
                    if (cid == cateId) {
                        flag = true;
                        break;
                    }
                }
                map.put("LAY_CHECKED", flag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DataGridViewResult(Long.valueOf(mapList.size()), mapList);

    }

    /**
     * 根据商品id加载商品信息
     * @param goodsid
     * @return
     */
    @GetMapping("/loadGoodsById")
    public DataGridViewResult loadGoodsById(int goodsid) {


        QueryWrapper<Goods> goodsQueryWrapper = new QueryWrapper<>();
        goodsQueryWrapper.eq(goodsid != 0, "gid", goodsid);
        Goods goods = goodsService.getById(goodsid);

        return new DataGridViewResult(goods);

    }

    /**
     * 为商品分配类别
     *
     * @param categoryids
     * @param goodsid
     * @return
     */
    @SysLog("类别添加操作")
    @RequestMapping("/saveGoodsCategory")
    public Result saveGoodsCategory(String categoryids, int goodsid) {

        try {
            if (goodsService.saveGoodsCategory(goodsid, categoryids)) {
                return Result.success(true, null, "分配成功");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error(false, null, "分配失败");

    }

    /**
     * 加载下拉框
     *
     * @return
     */
    @RequestMapping("/loadAllGoods")
    public DataGridViewResult loadAllGoods() {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        List<Goods> list = goodsService.list(queryWrapper);
        return new DataGridViewResult(list);

    }


    /**
     * 根据供应商查商品下拉框
     *
     * @param providerid
     * @return
     */
    @RequestMapping("/loadGoodsByProvidreId")
    public DataGridViewResult loadGoodsByProvidreId(Integer providerid) {
        QueryWrapper<Goods> goodsQueryWrapper = new QueryWrapper<>();
        goodsQueryWrapper.eq(providerid != null, "providerid", providerid);
        List<Goods> list = goodsService.list(goodsQueryWrapper);
        for (Goods goods : list) {
            Provider provider = providerService.getById(goods.getProviderid());
            if (null != provider) {
                goods.setProvidername(provider.getProvidername());
            }

        }
        return new DataGridViewResult(list);

    }
}
