package com.jjc.dreamproject.presenter.music;

import com.jjc.dreamproject.bean.QueryMusicEntity;
import com.jjc.dreamproject.contract.music.MusicSearchContract;
import com.jjc.dreamproject.model.music.MusicSearchModel;

import java.util.List;

public class MusicSearchPresenter extends MusicSearchContract.Presenter {

    public MusicSearchContract.Model attachModel() {
        return new MusicSearchModel(this);
    }

    @Override
    public void start(int number, String key) {
        M().startQuery(number,key);
    }

    @Override
    public void showRequest(boolean isSuceeed, List<QueryMusicEntity.DataBean.SongBean.ListBean> songs) {
        V().showQueryData(isSuceeed,songs);
    }
}

