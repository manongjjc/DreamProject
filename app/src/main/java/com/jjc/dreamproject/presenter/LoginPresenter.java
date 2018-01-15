package com.jjc.dreamproject.presenter;

import com.jjc.dreamproject.contract.LoginContract;
import com.jjc.dreamproject.model.LoginModel;

public class LoginPresenter extends LoginContract.Presenter {

    public LoginContract.Model attachModel() {
        return new LoginModel();
    }
}

