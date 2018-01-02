package com.jjc.dreamproject.presenter;

import com.jjc.dreamproject.contract.MainContract;
import com.jjc.dreamproject.model.MainModel;

public class MainPresenter extends MainContract.Presenter {

    public MainContract.Model attachModel() {
        return new MainModel(this);
    }

    @Override
    public void startLogin(String name, String pathword) {
        M().login(name,pathword);
    }

    @Override
    public void loginSucceed() {
        V().stopDialog(true,"");
    }

    @Override
    public void loginDefeated(String messge) {
        V().stopDialog(false,messge);
    }
}

