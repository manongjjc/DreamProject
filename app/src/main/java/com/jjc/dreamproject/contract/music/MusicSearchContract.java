package com.jjc.dreamproject.contract.music;

import com.jjc.dreamproject.bean.QueryMusicEntity;
import com.jjc.dreamproject.contract.BaseContract;

import java.util.List;

public interface MusicSearchContract {

    interface Model {
        void startQuery(int number,String key);
    }

    interface View extends BaseContract.View {
        void showQueryData(boolean isSuceeed,List<QueryMusicEntity.DataBean.SongBean.ListBean> songs);
    }

    abstract class Presenter extends BaseContract.Presenter<View, Model> {
        public abstract void start(int number,String key);
        public abstract void showRequest(boolean isSuceeed,List<QueryMusicEntity.DataBean.SongBean.ListBean> songs);
    }
}

