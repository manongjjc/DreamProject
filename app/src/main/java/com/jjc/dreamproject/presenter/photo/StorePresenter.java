package com.jjc.dreamproject.presenter.photo;

import com.jjc.dreamproject.bean.MediaBean;
import com.jjc.dreamproject.contract.photo.StoreContract;
import com.jjc.dreamproject.model.photo.StoreModel;

import java.util.List;

public class StorePresenter extends StoreContract.Presenter {

    public StoreContract.Model attachModel() {
        return new StoreModel(this);
    }

    @Override
    public void start(int number,int left) {
        M().startRequest(number,left);
    }

    @Override
    public void showStore(boolean isSucces,List<MediaBean> list,String alert) {
        V().returnRequest(isSucces,list,alert);
    }
}

