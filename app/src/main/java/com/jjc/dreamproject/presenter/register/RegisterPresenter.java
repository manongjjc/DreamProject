package com.jjc.dreamproject.presenter.register;

import com.jjc.dreamproject.contract.register.RegisterContract;
import com.jjc.dreamproject.model.register.RegisterModel;

public class RegisterPresenter extends RegisterContract.Presenter {

    public RegisterContract.Model attachModel() {
        return new RegisterModel(this);
    }

    @Override
    public void startRegist(String name, String pathword) {
        M().regist(name,pathword);
    }

    @Override
    public void returnResult(boolean isSuccess, String alert, String name, String pathword) {
        V().stopDialog(isSuccess,alert,name,pathword);
    }
}

