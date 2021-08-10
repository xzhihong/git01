package com.xxx.service;

import com.xxx.base.BaseService;
import com.xxx.bean.LogLogin;
import com.xxx.mapper.LogLoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LogLoginService extends BaseService<LogLogin,Integer> {
    @Resource
    private LogLoginMapper logLoginMapper;



}
