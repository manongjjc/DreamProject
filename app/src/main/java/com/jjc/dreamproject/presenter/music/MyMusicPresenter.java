package com.jjc.dreamproject.presenter.music;

import com.jjc.dreamproject.bean.MediaBean;
import com.jjc.dreamproject.contract.music.MyMusicContract;
import com.jjc.dreamproject.model.music.MyMusicModel;

import java.util.List;

public class MyMusicPresenter extends MyMusicContract.Presenter {

    public MyMusicContract.Model attachModel() {
        return new MyMusicModel(this);
    }

    @Override
    public void startRequest(int pager) {
        M().getMyMusic(pager);
    }

    @Override
    public void returnRequest(boolean isSucessed, List<MediaBean> mediaBeanList,String message) {
        V().showMusic(isSucessed,mediaBeanList,message);
    }
}

