package com.jjc.dreamproject.contract.music;

import com.jjc.dreamproject.bean.MediaBean;
import com.jjc.dreamproject.contract.BaseContract;

import java.util.List;

public interface MyMusicContract {

    interface Model {
        void getMyMusic(int pager);
    }

    interface View extends BaseContract.View {
        void showMusic(boolean isSucessed, List<MediaBean> mediaBeanList,String message);
    }

    abstract class Presenter extends BaseContract.Presenter<View, Model> {
        public abstract void startRequest(int pager);

        public abstract void returnRequest(boolean isSucessed, List<MediaBean> mediaBeanList,String message);
    }
}

