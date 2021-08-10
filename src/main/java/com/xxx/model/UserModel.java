package com.xxx.model;

public class UserModel {
    private String userid;
    private String loginname;
    private String realname;

    public UserModel() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "userid='" + userid + '\'' +
                ", loginname='" + loginname + '\'' +
                ", realname='" + realname + '\'' +
                '}';
    }
}
