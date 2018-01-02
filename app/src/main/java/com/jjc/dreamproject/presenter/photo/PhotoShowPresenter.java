package com.jjc.dreamproject.presenter.photo;

import com.jjc.dreamproject.bean.UrlPhotoEntity;
import com.jjc.dreamproject.contract.photo.PhotoShowContract;
import com.jjc.dreamproject.model.photo.PhotoShowModel;

import java.util.List;

public class PhotoShowPresenter extends PhotoShowContract.Presenter {

    public PhotoShowContract.Model attachModel() {
        return new PhotoShowModel(this);
    }

    @Override
    public void startRequest(int page) {
        M().getPhoto(page);
    }

    @Override
    public void returnPhotos(boolean isSuccess,List<UrlPhotoEntity.DataBean> beanList) {
        V().addPhotos(isSuccess,beanList);
    }
}

