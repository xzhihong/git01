package com.xxx.controller;

import com.xxx.base.BaseController;
import com.xxx.bean.Role;
import com.xxx.bean.RoleUser;
import com.xxx.query.RoleQuery;
import com.xxx.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("role")
public class RoleController extends BaseController {
    @Resource
    private RoleService roleService;


    //查询所有角色
    @RequestMapping("roles")
    @ResponseBody
    public List<Map<String, Object>> sayRole(Integer userid) {
        return roleService.findAllRoles(userid);
    }
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> list(RoleQuery query){
        return roleService.findUserByParam(query);
    }

}
