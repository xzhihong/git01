package com.xxx.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xxx.base.BaseService;
import com.xxx.bean.LogLogin;
import com.xxx.bean.RoleUser;
import com.xxx.bean.User;
import com.xxx.mapper.LogLoginMapper;
import com.xxx.mapper.RoleUserMapper;
import com.xxx.mapper.UserMapper;
import com.xxx.model.UserModel;
import com.xxx.query.UserQuery;
import com.xxx.utils.*;
import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class UserService extends BaseService<User, Integer> {
    @Resource
    private UserMapper userMapper;
    @Resource
    private LogLoginMapper logLoginMapper;
    @Resource
    private RoleUserMapper roleUserMapper;

    /**
     * ⽤户登录
     * 1. 验证参数
     * 姓名 ⾮空判断
     * 密码 ⾮空判断
     * 2. 根据⽤户名，查询⽤户对象
     * 3. 判断⽤户是否存在
     * ⽤户对象为空，记录不存在，⽅法结束
     * 4. ⽤户对象不为空
     * ⽤户存在，校验密码
     * 密码不正确，⽅法结束
     * 5. 密码正确
     * ⽤户登录成功，返回⽤户的相关信息 （定义UserModel类，返回⽤户某些信息）
     */
    public UserModel queryLoginName(String loginName, String pwd) {
        //1.验证参数
        checkLoginParams(loginName, pwd);
        //2,查询对象
        User user = userMapper.selectUserByLoginName(loginName);
        //3.
        AssertUtil.isTrue(user == null, "用户不存在或者已经注销");
        //4.用户存在,密码的校验
        checkLoginPwd(pwd, user.getPwd());
        LogLogin logLogin = new LogLogin();
        logLogin.setLoginname(user.getRealname() + "-" + user.getLoginname());
        logLogin.setLoginip("127.1.1.1.1");
        logLogin.setLogintime(new Date());
        logLoginMapper.insert(logLogin);


        //5.返回用户信息
        return modelUser(user);


    }

    /**
     * 实例化UserModel
     * @param user
     * @return
     */
    private UserModel modelUser(User user) {
        //实例化对象
        UserModel um = new UserModel();
        um.setLoginname(user.getLoginname());
        um.setRealname(user.getRealname());
        um.setUserid(UserIDBase64.encoderUserID(user.getUserid()));
        return um;

    }

    /**
     * 密码校验
     * @param pwd 输入密码
     * @param pwd1 数据库密码
     */
    private void checkLoginPwd(String pwd, String pwd1) {
        //加密比较

        pwd = Md5Util.encode(pwd);

        //比较
        AssertUtil.isTrue(!(pwd.equals(pwd1)), "输入密码不正确");
    }

    /**
     * 登录参数校验
     * @param loginName 登录名
     * @param pwd 密码
     */
    private void checkLoginParams(String loginName, String pwd) {
        AssertUtil.isTrue(StringUtils.isBlank(loginName), "用户名不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(pwd), "密码不能为空");


    }


    /**
     * 修改密码
     * 1)userId 存在，user对象  登录
     * 2)原始密码，非空，和密文密码一致
     * 3）新密码，非空，不能和原始密码一致
     * 4）确认密码 非空，确认密码和新密码一致
     * 5）修改密码，加密
     * 6）修改成功与否，<1;
     * @param userId 用户id
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @param confirmPwd 确认密码
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updatePassword(Integer userId, String oldPwd, String newPwd, String confirmPwd) {
        //根据用户UserId拿到当前对象
        User user = userMapper.selectByPrimaryKey(userId);
        //1.参数校验
        checkPwdParam(user, oldPwd, newPwd, confirmPwd);
        //2.修改密码加密
        user.setPwd(Md5Util.encode(newPwd));
        //3.修改是否成功
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user) < 1, "修改失败了");
    }

    /**
     * 验证参数列表
     *
     * @param user
     * @param oldPwd
     * @param newPwd
     * @param confirmPwd
     */
    private void checkPwdParam(User user, String oldPwd, String newPwd, String confirmPwd) {
        //1,user是否存在
        AssertUtil.isTrue(null == user, "用户未登录或者不存在");
        //判空
        AssertUtil.isTrue(StringUtils.isBlank(oldPwd), "请输入原始密码");
        //和登录密码一致
        AssertUtil.isTrue(!(user.getPwd().equals(Md5Util.encode(oldPwd))), "原始密码不正确");
        //新密码非空，不能和原始密码一致
        AssertUtil.isTrue(StringUtils.isBlank(newPwd), "请输入新密码");
        //不能和原始密码一致
        AssertUtil.isTrue(newPwd.equals(oldPwd), "新和原始不能一致");
        //确认密码非空，新密码和确认密码一致
        AssertUtil.isTrue(StringUtils.isBlank(confirmPwd), "确认密码不能为空");
        //新密码和确认密码一致
        AssertUtil.isTrue(!newPwd.equals(confirmPwd), "新密码和确认密码必须一致");
    }


    /**
     * 用户模块模糊查询
     * @param query 条件
     * @return
     */
    public Map<String, Object> findUserByParam(UserQuery query) {
        //实例
        Map<String, Object> map = new HashMap<String, Object>();
        //初始化分页单位
        PageHelper.startPage(query.getPage(), query.getLimit());
        //查询
        List<User> users = userMapper.selectByParams(query);
        //开始分页
        PageInfo<User> pageInfo = new PageInfo<>(users);

        //构建数据
        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }


    /**
     *用户模块的添加
     * 添加⽤户
     * 1.参数校验
     *⽤户名 ⾮空 唯⼀性
     *身份证 ⾮空
     *⼿机号 ⾮空 格式合法
     *2. 设置默认参数
     * userPwd 123456 -> md5加密
     *3. 执⾏添加，判断结果
     * @param user
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addUser(User user) {
        User temp = userMapper.selectUserByLoginName(user.getLoginname());
        AssertUtil.isTrue(temp != null, "用户已经存在");
        //1校验参数
        checkUserParam(user.getLoginname(), user.getPhone(), user.getIdentity());
        //设置默认参数
        user.setAvailable(1);
        user.setPwd(Md5Util.encode("123456"));

        //执行
        AssertUtil.isTrue(userMapper.insertHasKey(user) < 1, "添加失败");
        System.out.println(user.getUserid() + "<<<" + user.getRoleIds());

        //添加用户和角色信息
        addUseridAndRoleid(user.getUserid(), user.getRoleIds());

    }

    /**
     * 添加角色
     * @param userid 用户id
     * @param roleIds 角色id
     */
    private void addUseridAndRoleid(Integer userid, String roleIds) {
        //修改中的操作 统计当前用户的角色
        Integer i = roleUserMapper.countUserRoleByUserId(userid);
        if (i > 0) {
            //删除用户所有的角色重新添加角色信息
            AssertUtil.isTrue(roleUserMapper.deleteUserRoleByUserId(userid) < i, "删除角色失败");

        }


        //判断roleIds是否有数据
        if (StringUtils.isNotBlank(roleIds)) {
            //将userid和roleid存储到关系表的实体类
            List<RoleUser> list = new ArrayList<>();
            //分割
            String[] split = roleIds.split(",");
            //遍历
            for (String roleid : split) {
                //实例化对象
                RoleUser ru = new RoleUser();
                ru.setRid(Integer.parseInt(roleid));
                ru.setUid(userid);
                //存储到集合
                list.add(ru);
                //System.out.println(roleid);
                System.out.println(userid);
            }


            //判断添加是否成功
            AssertUtil.isTrue(roleUserMapper.insertBatch(list) != list.size(), "添加角色失败");

        }

    }


    /**
     * 校验参数
     * @param loginname 登录名
     * @param phone 手机号
     * @param identity 身份证
     */
    private void checkUserParam(String loginname, String phone, String identity) {
        //用户名feikong
        AssertUtil.isTrue(StringUtils.isBlank(loginname), "请输入用户名");
        User user = userMapper.selectUserByLoginName(loginname);
        //AssertUtil.isTrue(user!=null,"用户已经存在");
        //手机号
        AssertUtil.isTrue(StringUtils.isBlank(phone), "请输入手机号");
        //手机格式
        AssertUtil.isTrue(!PhoneUtil.isMobile(phone), "请输入合法的手机号");
        //身份证
        AssertUtil.isTrue(StringUtils.isBlank(identity), "身份证不能为空");


    }

    /**
     * 修改用户
     * @param user 输入的用户
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void changeUpdate(User user) {

        // 1. 参数校验
        checkUserParams(user.getPhone(),user.getRealname(),user.getIdentity());
        // 通过id查询⽤户对象
        User tp = userMapper.selectUserByLoginName(user.getLoginname());
        System.out.println(tp);
        System.out.println(user);

        //用户是否存在
        AssertUtil.isTrue(tp == null, "修改记录不存在");
        AssertUtil.isTrue(tp == null && !(tp.getUserid().equals(user.getUserid())), "用户已存在");

        //设置默认值
        user.setAvailable(1);
        //执行
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user) < 1, "修改失败");
        //修改中间表信息
        addUseridAndRoleid(user.getUserid(), user.getRoleIds());

    }

    private void checkUserParams(String phone, String realname, String identity) {
        AssertUtil.isTrue(StringUtils.isBlank(realname),"请输入真实姓名");
        //手机号
        AssertUtil.isTrue(StringUtils.isBlank(phone), "请输入手机号");
        //手机格式
        AssertUtil.isTrue(!PhoneUtil.isMobile(phone), "请输入合法的手机号");
        //身份证
        AssertUtil.isTrue(StringUtils.isBlank(identity), "身份证不能为空");


    }


    /**
     * 用户模块的单删除和批量删除
     * @param userids 用户id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void removeIds(Integer[] userids) {

        AssertUtil.isTrue(userids == null || userids.length == 0, "请选择删除数据");

        AssertUtil.isTrue(userMapper.deleteBatch(userids) < 1, "删除失败了");

        //遍历
        for (Integer userid : userids) {
            //修改中的操作 统计当前用户的角色
            Integer i = roleUserMapper.countUserRoleByUserId(userid);
            if (i > 0) {
                //删除用户所有的角色重新添加角色信息
                AssertUtil.isTrue(roleUserMapper.deleteUserRoleByUserId(userid) < i, "删除角色失败");

            }

        }


    }


}
