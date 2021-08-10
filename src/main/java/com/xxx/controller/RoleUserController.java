package com.xxx.controller;

import com.xxx.base.BaseController;
import com.xxx.service.RoleUserService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class RoleUserController extends BaseController {
    @Resource
    private RoleUserService roleUserService;
}
