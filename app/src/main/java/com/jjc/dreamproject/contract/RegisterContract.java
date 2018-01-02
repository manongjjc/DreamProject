package com.jjc.dreamproject.contract;

import com.jjc.dreamproject.presenter.RegisterPresenter;

public interface RegisterContract {

    interface Model {
        void regist(String name, String pathword, RegisterPresenter registerPresenter);
    }

    interface View extends BaseContract.View {
        void stopDialog(boolean isSuccess,String alert,String name,String pathword);
    }

    abstract class Presenter extends BaseContract.Presenter<View, Model> {
        public abstract void startRegist(String name,String pathword);

        public abstract void returnResult(boolean isSuccess,String alert,String name,String pathword);
    }
}

