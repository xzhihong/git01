package com.xxx.mapper;

import com.xxx.base.BaseMapper;
import com.xxx.bean.RoleUser;

public interface RoleUserMapper extends BaseMapper<RoleUser,Integer> {

    //统计当前用户拥有的角色
    Integer countUserRoleByUserId(Integer uid);

    //删除当前用户的的所有角色信息
    Integer deleteUserRoleByUserId(Integer uid);

}