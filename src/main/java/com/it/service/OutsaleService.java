package com.it.service;

import com.it.entity.Outsale;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 
 * @since 2023-03-26
 */
public interface OutsaleService extends IService<Outsale> {

    void addOutsale(Integer id, Integer number, String remark, Double money, HttpSession session);
}
