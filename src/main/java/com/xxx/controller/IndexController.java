package com.xxx.controller;

import com.xxx.base.BaseController;
import com.xxx.bean.User;
import com.xxx.service.UserService;
import com.xxx.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController extends BaseController {
    @Autowired
    private UserService userService;


    /**
     * 跳转登录页面
     * @return
     */
    @RequestMapping("index")
    public String sayIndex(){
        return "index";
    }


    @RequestMapping("welcome")
    public String welcome(){
        return "welcome";
    }

    /**
     * 跳转到mian页面
     * @param req
     * @return
     */
    @RequestMapping("main")
    public String main(HttpServletRequest req){
        //获取cookie
        int userid = LoginUserUtil.releaseUserIdFromCookie(req);
        //System.out.println(userid);
        //查询用户信息
        User user = userService.selectByPrimaryKey(userid);
        //存储
        req.setAttribute("user",user);
        //System.out.println(user);


        return "main";
    }

}
