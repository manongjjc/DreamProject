package com.jjc.dreamproject.contract.register;

import com.jjc.dreamproject.contract.BaseContract;

public interface RegisterContract {

    interface Model {
        void regist(String name, String pathword);
    }

    interface View extends BaseContract.View {
        void stopDialog(boolean isSuccess,String alert,String name,String pathword);
    }

    abstract class Presenter extends BaseContract.Presenter<View, Model> {
        public abstract void startRegist(String name,String pathword);

        public abstract void returnResult(boolean isSuccess,String alert,String name,String pathword);
    }
}

