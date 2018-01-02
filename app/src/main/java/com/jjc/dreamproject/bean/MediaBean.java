package com.jjc.dreamproject.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by JJC on 2017/12/4.
 */

public class MediaBean extends BmobObject {
    //媒体文件类型
    private int tylp;
    private String title;
    private String url;
    private String photoUrl;
    private String lyricUrl;
    private String singerName;

    public int getTylp() {
        return tylp;
    }

    public void setTylp(int tylp) {
        this.tylp = tylp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getLyricId() {
        return lyricUrl;
    }

    public void setLyricUrl(String lyricUrl) {
        this.lyricUrl = lyricUrl;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    @Override
    public String toString() {
        return "MediaBean{" +
                "tylp=" + tylp +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", lyricId='" + lyricUrl + '\'' +
                ", singerName='" + singerName + '\'' +
                '}';
    }
}
