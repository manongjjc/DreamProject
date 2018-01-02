package com.jjc.dreamproject.contract.photo;

import com.jjc.dreamproject.bean.UrlPhotoEntity;
import com.jjc.dreamproject.contract.BaseContract;

import java.util.List;

public interface PhotoShowContract {

    interface Model {
        void getPhoto(int page);
    }

    interface View extends BaseContract.View {
        void addPhotos(boolean isSuccess,List<UrlPhotoEntity.DataBean> beanList);
    }

    abstract class Presenter extends BaseContract.Presenter<View, Model> {
        public abstract void startRequest(int page);

        public abstract void returnPhotos(boolean isSuccess,List<UrlPhotoEntity.DataBean> beanList);
    }
}

