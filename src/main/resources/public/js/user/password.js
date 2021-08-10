layui.use(['form', 'jquery','layer', 'jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);


    //提交表单
    form.on("submit(saveBtn)", function (data) {
        var dataField = data.field;
        //修改
        $.ajax({
            type:"post",
            url:ctx+"/user/updatePwd",
            data:{
                "oldPwd":dataField.old_password,
                "newPwd":dataField.new_password,
                "confirmPwd":dataField.again_password
            },
            dataType:"json",
            success:function (data){
                if(data.code==200){
                    layer.msg("修改密码成功了，马上消失",function(){
                        //清空Cookie
                        $.removeCookie("userid",{domain:"localhost",path:"/carrent"});
                        $.removeCookie("loginname",{domain:"localhost",path:"/carrent"});
                        $.removeCookie("realname",{domain:"localhost",path:"/carrent"});
                        //跳转
                        window.parent.location.href=ctx+"/index";
                    });
                }else{
                    layer.msg(data.msg);
                }
            }
        });
        //阻止表单跳转
        return false;
    });
});