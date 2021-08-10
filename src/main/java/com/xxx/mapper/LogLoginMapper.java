package com.xxx.mapper;

import com.xxx.base.BaseMapper;
import com.xxx.bean.LogLogin;

public interface LogLoginMapper extends BaseMapper<LogLogin,Integer> {

    public void insert(LogLogin logLogin);

}