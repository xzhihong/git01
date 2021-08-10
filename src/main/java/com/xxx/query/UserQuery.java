package com.xxx.query;

import com.xxx.base.BaseQuery;

public class UserQuery extends BaseQuery {
    private String loginname;
    private String identity;
    private String phone;

    public UserQuery() {
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserQuery{" +
                "loginname='" + loginname + '\'' +
                ", identity='" + identity + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
