package com.xxx.mapper;

import com.xxx.base.BaseMapper;
import com.xxx.bean.Role;
import com.xxx.query.RoleQuery;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role,Integer> {

    @MapKey("")
    //查询所有角色
    public List<Map<String,Object>> selectAllByRole (Integer userid);

    /**
     * 条件查询角色信息
     * @param query
     * @return
     */
    public List<Role> selectAllByQuery(RoleQuery query);
}