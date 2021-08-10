layui.use(['form', 'jquery','layer' ,'jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);


    //提交表单
    form.on("submit(login)", function (data) {
        // data.field.username;
        var dataField = data.field;

        //验证用户名
        if (dataField.username == "undefinded" || dataField.username.trim == "") {
            layer.msg("用户名不能为空");
            return;
        }
        if (dataField.password == "undefinded" || dataField.password.trim == "") {
            layer.msg("密码不能为空");
            return;
        }
        //发送ajax
        $.ajax({
            type: "post",
            url: ctx + "/user/login",
            data: {
                "loginName": dataField.username,
                "pwd": dataField.password
            },
            dataType: "json",
            success: function (data) {
                if (data.code == 200) {
                    layer.msg("登录成功了", function () {
                        //存储数据到cookie
                        $.cookie("userid", data.result.userid);
                        $.cookie("loginname", data.result.loginname);
                        $.cookie("realname", data.result.realname);
                        //判断
                        if($("#rememberMe").is(":checked")){
                            //存储数据到cookie
                            $.cookie("userid", data.result.userid,{expires:7});
                            $.cookie("loginame", data.result.loginname,{expires: 7});
                            $.cookie("realname", data.result.realname,{expires:7 });
                        }
                        //跳转页面
                        window.location.href = ctx + "/main";
                    });

                } else {
                    layer.msg(data.msg);
                }
            }
        });
        //阻止表单跳转
        return false;
    });
});