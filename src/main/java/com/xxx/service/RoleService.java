package com.xxx.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxx.base.BaseService;
import com.xxx.bean.Role;
import com.xxx.mapper.RoleMapper;
import com.xxx.query.RoleQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleService extends BaseService<Role,Integer> {
    @Resource
    private RoleMapper roleMapper;

    /**
     * 查询所有角色
     * @param userid 用户id
     * @return
     */
    public List<Map<String,Object>> findAllRoles(Integer userid){

        return roleMapper.selectAllByRole(userid);
    }


    public Map<String,Object> findUserByParam(RoleQuery query){
        //实例化map
        Map<String,Object> map=new HashMap<>();
        //开启分页单位
        PageHelper.startPage(query.getPage(),query.getLimit());
        //查询
        List<Role> roles = roleMapper.selectAllByQuery(query);
        //分页
        PageInfo<Role> pi=new PageInfo<Role>(roles);
        //构建数据
        //构建数据
        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", pi.getTotal());
        map.put("data", pi.getList());
        return map;


    }
}
