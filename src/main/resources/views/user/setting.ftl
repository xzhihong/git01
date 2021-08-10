<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>基本资料</title>
    <#include "../common.ftl">
    <style>
        .layui-form-item .layui-input-company {width: auto;padding-right: 10px;line-height: 38px;}
    </style>
</head>
<body>
<div class="layuimini-container">
    <div class="layuimini-main">
        <div class="layui-form layuimini-form">
            <div class="layui-form-item">
                <label class="layui-form-label required">管理账号</label>
                <div class="layui-input-block">
                    <input type="text" name="loginname" lay-verify="required" readonly="readonly" class="layui-input" value="${(user.loginname)!}">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label required">手机号</label>
                <div class="layui-input-block">
                    <input type="number" name="phone" lay-verify="required" lay-reqtext="手机不能为空" placeholder="请输入手机"
                           value="${(user.phone)!}" class="layui-input">
                </div>

            </div>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">身份证</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input identity"
                           lay-verify="identity" name="identity" value="${(user.identity)!}"
                           id="identity"
                           placeholder="请输入身份证">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">真实姓名</label>
                <div class="layui-input-block">
                    <input type="text" name="realname"  placeholder="请输入真实姓名"  value="${(user.realname)!}" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <input type="hidden" name="userid"   value="${(user.userid)!}" >
                    <button class="layui-btn" lay-submit lay-filter="saveBtn">确认保存</button>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctx}/js/user/setting.js"></script>
</body>
</html>