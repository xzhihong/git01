layui.use(['form', 'layer','formSelects'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;
    var formSelects =layui.formSelects;


    /*取消*/
    $("#closeBtn").click(function(){
        //假设这是iframe页
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
    });


    //添加
    form.on("submit(addOrUpdateUser)",function(obj){
        //加载层
        var index=top.layer.msg("数据加载中...",{icon:16,time:false,shade:0.8});
        var url=ctx+"/user/save";


        if(obj.field.userid){

            url=ctx+"/user/update"
        }
        //添加
        $.ajax({
            type:"post",
            url:url,
            data:obj.field,
            dataType:"json",
            success:function (data){
                if(data.code==200){
                    //关闭中间层
                    top.layer.close(index);
                    //关闭iframe
                    top.layer.closeAll("iframe");
                    top.layer.msg("操作成功",{icon:6});
                    //刷新父路径
                    parent.location.reload()
                }else{
                    //失败提示信息
                    layer.msg(data.msg,{icon:5});
                }
            }
        });
        //阻止表单提交
        return false;
    });


    /*加载下拉框*/
    var userid=$("input[name='userid']").val();
    formSelects.config('selectId', {
        type: 'post',                //请求方式: post, get, put, delete...
        searchUrl: ctx+'/role/roles?userid='+userid,              //搜索地址, 默认使用xm-select-search的值, 此参数优先级高
        keyName: 'rolename',            //自定义返回数据中name的key, 默认 name
        keyVal: 'roleid',            //自定义返回数据中value的key, 默认 value

    }, true);

});