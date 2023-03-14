package com.it.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.aspect.SysLog;
import com.it.vo.LogVO;
import com.it.entity.Logs;
import com.it.service.LogsService;
import com.it.utils.DataGridViewResult;
import com.it.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 
 * @since 2023-03-03
 */
@RestController
@RequestMapping("/logs")
public class LogsController {

    @Autowired
    private LogsService logsService;

    /**
     * 日志模糊查询
     * @param logVO
     * @return
     */
    @SysLog("日志查询操作")
    @RequestMapping("/logList")
    public DataGridViewResult logList(LogVO logVO) {
        //创建分页信息    参数1 当前页  参数2 每页显示条数
        IPage<Logs> page = new Page<>(logVO.getPage(), logVO.getLimit());
        QueryWrapper<Logs> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(logVO.getType()),"type", logVO.getType());
        queryWrapper.like(!StringUtils.isEmpty(logVO.getUname()),"uname", logVO.getUname());
        queryWrapper.ge(logVO.getStartTime()!=null,"ltime", logVO.getStartTime());
        queryWrapper.le(logVO.getEndTime()!=null,"ltime", logVO.getEndTime());
        IPage<Logs> logsIPage = logsService.page(page, queryWrapper);

        /**
         * logsIPage.getTotal() 总条数
         * logsIPage.getRecords() 分页记录列表
         */
        return new DataGridViewResult(logsIPage.getTotal(),logsIPage.getRecords());
    }

    /**
     * 日志批量删除
     * @param ids
     * @return
     */
    @SysLog("日志删除操作")
    @RequestMapping("/delete")
    public Result logList(String ids) {
        //将字符串拆分成数组
        String[] idsStr = ids.split(",");
        List<String> list = Arrays.asList(idsStr);
        boolean bool = logsService.removeByIds(list);
        if(bool){
            return Result.success(true,"200","删除成功！");
        }
        return Result.error(false,null,"删除失败！");
    }




}
