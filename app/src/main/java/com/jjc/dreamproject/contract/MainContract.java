package com.jjc.dreamproject.contract;

public interface MainContract {

    interface Model {
        void login(String name, String pathword);
    }

    interface View extends BaseContract.View {
        void stopDialog(boolean isSucceed,String alert);
    }

    abstract class Presenter extends BaseContract.Presenter<View, Model> {
        public abstract void startLogin(String name,String pathword);

        public abstract void loginSucceed();

        public abstract void loginDefeated(String messge);
    }
}

