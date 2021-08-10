package com.xxx.mapper;

import com.xxx.base.BaseMapper;
import com.xxx.bean.User;
import com.xxx.query.UserQuery;


import java.util.List;


public interface UserMapper extends BaseMapper<User, Integer> {

    //根据用户登录名查询对象信息
    public User selectUserByLoginName(String loginName);

    /**
     * 条件查询用户
     * @param query
     * @return
     */
    public List<User> selectByParams(UserQuery query);
}