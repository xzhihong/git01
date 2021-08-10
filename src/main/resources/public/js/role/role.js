layui.use(['table', 'layer'], function () {
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    //角色列表展示
    var tableIns = table.render({
        elem: '#roleList',
        url: ctx + '/role/list',
        cellMinWidth: 95,
        page: true,
        height: "full-125",
        limits: [10, 15, 20, 25],
        limit: 10,
        toolbar: "#toolbarDemo",
        id: "roleListTable",
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
            {field: "roleid", title: 'ID', fixed: "true", width: 80},
            {field: 'rolename', title: '角色名', minWidth: 50, align: "center"},
            {field: 'roledesc', title: '角色备注', minWidth: 100, align: 'center'},
            {field: 'creattime', title: '创建时间', align: 'center', minWidth: 150},
            {field: 'updatetime', title: '更新时间', align: 'center', minWidth: 150},
            {title: '操作', minWidth: 150, templet: '#roleListBar', fixed: "right", align: "center"}
        ]]
    });
    // 多条件搜索
    $(".search_btn").on("click", function () {
        table.reload("roleListTable", {
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                rolename: $("input[name='rolename']").val(),
                roledesc: $("input[name='roledesc']").val()
            }
        })
    });



});