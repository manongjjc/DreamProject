package com.jjc.dreamproject.observer;

import com.jjc.dreamproject.bean.MediaBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JJC on 2017/12/14.
 */

public class StoreMusicObserver {
    private static StoreMusicObserver observer;
    private List<MediaBean> list;
    private StoreMusicObserver(){
        list = new ArrayList<>();
    }

    public static StoreMusicObserver instance(){
        if(observer==null)
            observer = new StoreMusicObserver();
        return observer;
    }

    public void removeAll(){
        if(list!=null)
            list.clear();
    }

    public void addList(List<MediaBean> list){
        this.list.addAll(list);
    }

    public List<MediaBean> getMedias(){
        return list;
    }
}
