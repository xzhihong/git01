package com.xxx.controller;

import com.xxx.base.BaseController;
import com.xxx.base.ResultInfo;
import com.xxx.bean.User;
import com.xxx.model.UserModel;
import com.xxx.query.UserQuery;
import com.xxx.service.LogLoginService;
import com.xxx.service.UserService;
import com.xxx.utils.LoginUserUtil;
import freemarker.core.NonStringOrTemplateOutputException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.Socket;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;


    /**
     * 登录
     * @param loginName
     * @param pwd
     * @return
     */
    @RequestMapping("login")
    @ResponseBody
    public ResultInfo saylogin(String loginName, String pwd) {
        ResultInfo re = new ResultInfo();
        UserModel userModel = userService.queryLoginName(loginName, pwd);
        re.setResult(userModel);
        return re;
    }

    /**
     * 跳转修改密码页面
     * @return
     */
    @RequestMapping("toPasswordPage")
    public String sayUpdate() {
        return "user/password";
    }

    /**
     * 跳转到用户管理
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "user/user";
    }

    @RequestMapping("addOrUpdatePage")
    public String addUpdate(Integer userid, Model model) {
        //判断
        if(userid!=null){
            //查询对象信息
            User user = userService.selectByPrimaryKey(userid);
            //存储到作用域
            model.addAttribute("user",user);

        }

        return "user/add_update";
    }


    //修改密码
    @ResponseBody
    @PostMapping("updatePwd")
    public ResultInfo updatePwd(HttpServletRequest req, String oldPwd, String newPwd, String confirmPwd) {
        ResultInfo re = new ResultInfo();
        int userid = LoginUserUtil.releaseUserIdFromCookie(req);
        //调用方法
        userService.updatePassword(userid, oldPwd, newPwd, confirmPwd);
        return re;
    }

    //个人资料页面的跳转
    @RequestMapping("toSettingPage")
    public String setting(HttpServletRequest req, Model model) {
        int userid = LoginUserUtil.releaseUserIdFromCookie(req);
        User user = userService.selectByPrimaryKey(userid);
        model.addAttribute("user", user);
        return "user/setting";

    }


    //用户模块的搜索
        @RequestMapping("list")
        @ResponseBody
        public Map<String,Object> list(UserQuery query){

        return userService.findUserByParam(query);

        }

        //用户模块的添加
    @RequestMapping("save")
    @ResponseBody
    public  ResultInfo save(User user){
        userService.addUser(user);

        return success("用户添加成功");
    }

    //修改个人资料
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo update(User user){

        userService.changeUpdate(user);
        return success("修改成功");

    }



    /**
     * 删除⽤户
     * @param userids
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteUser(Integer[] userids){
        System.out.println("进来了");
        for (int i = 0; i < userids.length; i++) {
            System.out.println(userids[i]+">>>");
        }
        userService.removeIds(userids);
        return success("⽤户记录删除成功");
    }


}
