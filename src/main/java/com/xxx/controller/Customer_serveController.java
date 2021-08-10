package com.xxx.controller;

import com.xxx.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("customer_serve")
public class Customer_serveController extends BaseController {
    /**
     * 页面跳转用户管理
     * @return
     */
    @RequestMapping("index/3")
    public String index3() {
        return "user/user";
    }

    /**
     * 角色页面跳转
     * @return
     */
    @RequestMapping("index/2")
    public String index2(){
        return "role/role";
    }
}
