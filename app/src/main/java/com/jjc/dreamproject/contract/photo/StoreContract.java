package com.jjc.dreamproject.contract.photo;

import com.jjc.dreamproject.bean.MediaBean;
import com.jjc.dreamproject.contract.BaseContract;

import java.util.List;

public interface StoreContract {

    interface Model {
        void startRequest(int number,int left);
    }

    interface View extends BaseContract.View {
        void returnRequest(boolean isSucces,List<MediaBean> medias,String alert);
    }

    abstract class Presenter extends BaseContract.Presenter<View, Model> {
        public abstract void start(int number,int left);

        public abstract void showStore(boolean isSucces,List<MediaBean> list,String alert);
    }
}

