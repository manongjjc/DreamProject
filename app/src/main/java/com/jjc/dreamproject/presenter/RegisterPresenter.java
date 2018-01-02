package com.jjc.dreamproject.presenter;

import com.jjc.dreamproject.contract.RegisterContract;
import com.jjc.dreamproject.model.RegisterModel;

public class RegisterPresenter extends RegisterContract.Presenter {

    public RegisterContract.Model attachModel() {
        return new RegisterModel();
    }

    @Override
    public void startRegist(String name, String pathword) {
        M().regist(name,pathword,this);
    }

    @Override
    public void returnResult(boolean isSuccess, String alert, String name, String pathword) {
        V().stopDialog(isSuccess,alert,name,pathword);
    }

}

