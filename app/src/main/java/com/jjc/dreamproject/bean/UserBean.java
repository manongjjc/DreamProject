package com.jjc.dreamproject.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by JJC on 2017/11/29.
 */

public class UserBean extends BmobObject {
    private String name;
    private String pathword;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPathword() {
        return pathword;
    }

    public void setPathword(String pathword) {
        this.pathword = pathword;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "name='" + name + '\'' +
                ", pathword='" + pathword + '\'' +
                '}';
    }
}
