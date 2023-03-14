package com.it.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.aspect.SysLog;
import com.it.vo.ProviderVO;
import com.it.entity.Provider;
import com.it.entity.User;
import com.it.service.ProviderService;
import com.it.utils.DataGridViewResult;
import com.it.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 
 * @since 2023-03-04
 */
@RestController
@RequestMapping("/provider")
public class ProviderController {


    @Autowired
    private ProviderService providerService;


    /**
     * 供应商模糊查询
     * @param
     * @return
     */
    @SysLog("供应商查询操作")
    @RequestMapping("/providerList")
    public DataGridViewResult providerList(ProviderVO providerVO) {

        //创建分页信息    参数1 当前页  参数2 每页显示条数
        IPage<Provider> page = new Page<>(providerVO.getPage(), providerVO.getLimit());
        QueryWrapper<Provider> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(providerVO.getProvidername()),"providername", providerVO.getProvidername());
        queryWrapper.like(!StringUtils.isEmpty(providerVO.getTelephone()),"telephone", providerVO.getTelephone());
        IPage<Provider> providerIPage = providerService.page(page, queryWrapper);

        /**
         * logsIPage.getTotal() 总条数
         * logsIPage.getRecords() 分页记录列表
         */
        return new DataGridViewResult(providerIPage.getTotal(),providerIPage.getRecords());

    }

    /**
     * 供应商批量删除
     * @param ids
     * @return
     */
    @SysLog("供应商删除操作")
    @RequestMapping("/deleteList")
    public Result deleteList(String ids) {
        //将字符串拆分成数组
        String[] idsStr = ids.split(",");
        List<String> list = Arrays.asList(idsStr);
        boolean bool = providerService.removeByIds(list);
        if(bool){
            return Result.success(true,"200","删除成功！");
        }
        return Result.error(false,null,"删除失败！");
    }

    /**
     * 添加供应商信息
     * @param provider
     * @param session
     * @return
     */
    @SysLog("供应商添加操作")
    @PostMapping("/addprovider")
    public Result addProvider(Provider provider, HttpSession session){
        User user = (User) session.getAttribute("username");
        provider.setOpername(user.getUname());
        boolean bool = providerService.save(provider);
        if(bool){
            return Result.success(true,"200","添加成功！");
        }
        return Result.error(false,null,"添加失败！");
    }

    /**
     * 修改供应商信息
     * @param provider
     * @return
     */
    @SysLog("供应商修改操作")
    @PostMapping("/updateprovider")
    public Result updateProvider(Provider provider){

        boolean bool = providerService.updateById(provider);
        if(bool){
            return Result.success(true,"200","修改成功！");
        }
        return Result.error(false,null,"修改失败！");
    }

    /**
     * 删除单条数据
     * @param id
     * @return
     */
    @SysLog("供应商删除操作")
    @RequestMapping("/deleteOne")
    public Result deleteOne(int id) {

        boolean bool = providerService.removeById(id);
        if(bool){
            return Result.success(true,"200","删除成功！");
        }
        return Result.error(false,null,"删除失败！");
    }


    /**
     *
     * 加载下拉框
     * @return
     */
    @RequestMapping("/loadAllProvider")
    public DataGridViewResult loadAllProvider(){
        QueryWrapper<Provider> queryWrapper = new QueryWrapper<>();
        List<Provider> list = providerService.list(queryWrapper);
        return new DataGridViewResult(list);

    }


}
