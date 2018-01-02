package com.jjc.dreamproject.contract;

public interface FirstContract {

    interface Model {

    }

    interface View extends BaseContract.View {

    }

    abstract class Presenter extends BaseContract.Presenter<View, Model> {

    }
}

