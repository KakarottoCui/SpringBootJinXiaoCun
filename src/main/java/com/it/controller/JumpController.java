package com.it.controller;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class JumpController {

    /**
     * 登录页面跳转到后台首页
     * @return
     */
    @RequestMapping("/index")
    public String toIndex(){
        return "system/home/index";
    }

    /**
     * 登出  用的是shiro默认的logout
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:login";
    }

    /**
     *跳转到首页工作台
     */
    @RequestMapping("/toDes")
    public String toDesktop(){
        return "system/home/desktopManager";
    }

    /**
     * 跳转到日志管理页面
     *
     */
    @RequestMapping("/toLog")
    public String toLog(){
        return "system/log/logsManager";
    }

    /**
     * 跳转到供应商管理页面
     *
     */
    @RequestMapping("/toProvider")
    public String toProvider(){
        return "system/provider/providerManager";
    }

    /**
     * 跳转到客户管理页面
     * @return
     */
    @RequestMapping("/toCustomer")
    public String toCustomer(){
        return "system/customer/customerManager";
    }

    /**
     * 跳转到商品类别管理页面
     * @return
     */
    @RequestMapping("/toCategory")
    public String toCategory(){
        return "system/category/categoryManager";
    }

    /**
     * 跳转到权限管理页面
     * @return
     */
    @RequestMapping("/toPermission")
    public String toPermission() {
        return "system/permission/permissionManager";
    }
    /**
     * 跳转到权限管理页面-left
     * @return
     */
    @RequestMapping("/toPermissionLeft")
    public String toPermissionLeft() {
        return "system/permission/left";
    }
    /**
     * 跳转到权限管理页面-right
     * @return
     */
    @RequestMapping("/toPermissionRight")
    public String toPermissionRight() {
        return "system/permission/right";
    }

    /**
     * 跳转到角色管理页面
     * @return
     */
    @RequestMapping("/toRole")
    public String toRole() {
        return "system/role/roleManager";
    }

    /**
     * 跳转到用户管理页面
     * @return
     */
    @RequestMapping("/toUser")
    public String toUser() {
        return "system/user/userManager";
    }

    /**
     * 跳转到商品管理页面
     * @return
     */
    @RequestMapping("/toGoods")
    public String toGoods() {
        return "system/goods/goodsManager";
    }


    /**
     * 跳转到进货管理页面
     * @return
     */
    @RequestMapping("/toInport")
    public String toInport() {
        return "system/inport/inportManager";
    }

    /**
     * 跳转到退货管理页面
     * @return
     */
    @RequestMapping("/toOutport")
    public String toOutport() {
        return "system/outport/outportManager";
    }



    /**
     * 跳转到商品销售管理页面
     * @return
     */
    @RequestMapping("/toSale")
    public String toSale() {
        return "system/sale/saleManager";
    }


    /**
     * 跳转到销售退货管理页面
     * @return
     */
    @RequestMapping("/toOutsale")
    public String toOutsale() {
        return "system/outsale/outsaleManager";
    }

    /**
     * 跳转到销售退货管理页面
     * @return
     */
    @RequestMapping("/toReport")
    public String toReport() {
        return "system/report/reportManager";
    }
}
