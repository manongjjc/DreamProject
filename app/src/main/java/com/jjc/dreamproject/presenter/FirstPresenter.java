package com.jjc.dreamproject.presenter;

import com.jjc.dreamproject.contract.FirstContract;
import com.jjc.dreamproject.model.FirstModel;

public class FirstPresenter extends FirstContract.Presenter {

    public FirstContract.Model attachModel() {
        return new FirstModel();
    }
}

