package com.jjc.dreamproject.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by JJC on 2017/12/7.
 */

public class SpecialEntity extends BmobObject {
    private String url;
    private String describe;
    private String json;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    @Override
    public String toString() {
        return "SpecialEntity{" +
                "url='" + url + '\'' +
                ", describe='" + describe + '\'' +
                ", json='" + json + '\'' +
                '}';
    }
}
