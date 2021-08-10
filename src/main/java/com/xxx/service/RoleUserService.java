package com.xxx.service;

import com.xxx.base.BaseService;
import com.xxx.bean.RoleUser;
import com.xxx.mapper.RoleUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoleUserService extends BaseService<RoleUser, Integer> {
    @Resource
    private RoleUserMapper roleUserMapper;

}
