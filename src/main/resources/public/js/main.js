layui.use(['element', 'layer', 'layuimini','jquery','jquery_cookie'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        $ = layui.jquery_cookie($);

    // 菜单初始化
    $('#layuiminiHomeTabIframe').html('<iframe width="100%" height="100%" frameborder="0"  src="welcome"></iframe>')
    layuimini.initTab();

    /*选择元素，绑定事件*/
    $(".login-out").click(function (){
        //清空Cookie
        //清空Cookie
        $.removeCookie("userid",{domain:"localhost",path:"/carrent"});
        $.removeCookie("loginname",{domain:"localhost",path:"/carrent"});
        $.removeCookie("realname",{domain:"localhost",path:"/carrent"});
        //跳转
        window.parent.location.href=ctx+"/index";
    });
});