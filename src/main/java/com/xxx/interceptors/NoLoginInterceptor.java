package com.xxx.interceptors;

import com.xxx.exceptions.NoLoginException;
import com.xxx.service.UserService;
import com.xxx.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoLoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //用户未登录
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        //判断
        if(userId ==null || null==userService.selectByPrimaryKey(userId) ){
            throw  new NoLoginException();
        }
        //放行
        return  true;
    }
}
